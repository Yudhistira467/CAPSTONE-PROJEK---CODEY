import { Firestore } from '@google-cloud/firestore';
import { Storage } from '@google-cloud/storage';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Service account untuk Firestore
const firestoreKeyPath = path.resolve(__dirname, '../config/answer-service-account.json');
const db = new Firestore({
  projectId: 'codey-capstone',
  keyFilename: firestoreKeyPath,
});

// Service account untuk Cloud Storage
const storageKeyPath = path.resolve(__dirname, '../config/serviceAccountKey.json');
const storage = new Storage({
  projectId: 'capstone-3131',
  keyFilename: storageKeyPath,
});

const bucketName = 'edu-app-python-material';

// Fungsi untuk memuat soal dari Google Cloud Storage
async function loadSoalFromStorage() {
  try {
    const bucket = storage.bucket(bucketName);
    const file = bucket.file('question/gabungan.json'); // Path soal di bucket
    const [contents] = await file.download();
    const soalData = JSON.parse(contents.toString()); // Parse JSON dari file

    // Validasi apakah data soal memiliki properti quiz berbentuk array
    if (Array.isArray(soalData.quiz)) {
      return soalData.quiz; // Kembalikan array soal
    } else {
      throw new Error('Quiz data is not an array');
    }
  } catch (error) {
    throw new Error('Failed to load soal: ' + error.message);
  }
}

// Fungsi untuk menyimpan beberapa jawaban pengguna ke Firestore
export async function saveAnswers(req, res) {
  const { id, username, answers } = req.body;

  if (!id || !username || !answers || !Array.isArray(answers)) {
    return res.status(400).json({ message: 'ID, username, and answers must be provided and answers must be an array.' });
  }

  try {
    const soalData = await loadSoalFromStorage();

    const answersToSave = answers.map(answer => {
      const { questionId, userAnswer, materi } = answer;

      const soal = soalData.find((item) => item.question_id === questionId);
      if (!soal) {
        throw new Error(`Question with ID ${questionId} not found.`);
      }

      const correctAnswer = soal.correct_answer;
      const isCorrect = userAnswer.trim().toLowerCase() === correctAnswer.trim().toLowerCase(); // Perbandingan case insensitive

      // Tambahkan instruction jika jawaban salah
      const instruction = !isCorrect ? soal.instruction : null;

      return {
        questionId,
        userAnswer,
        correctAnswer,
        isCorrect,
        materi,
        instruction,
        timestamp: new Date().toISOString() // Buat timestamp secara terpisah
      };
    });

    await db.collection('user_answer').doc(id).set({
      userId: id,
      username,
      answers: answersToSave
    });

    res.status(200).json({
      success: true,
      message: 'Answers saved successfully.',
      answers: answersToSave
    });
  } catch (error) {
    res.status(500).json({ message: 'Error saving answers: ' + error.message });
  }
}

// Fungsi untuk menghitung skor pengguna
export async function getScore(req, res) {
  const { id } = req.query; // Dapatkan userId dari query parameter

  if (!id) {
    return res.status(400).json({ message: 'User ID is required.' });
  }

  try {
    console.log('User ID:', id);

    // Query Firestore untuk mendapatkan dokumen jawaban dari pengguna berdasarkan field userId
    const userAnswersSnapshot = await db.collection('user_answer').where('userId', '==', id).get();

    if (userAnswersSnapshot.empty) {
      console.log(`No answers found for user ID: ${id}`);
      return res.status(404).json({ message: 'No answers found for this user.' });
    }

    const userAnswersDoc = userAnswersSnapshot.docs[0]; // Ambil dokumen pertama yang cocok
    const userAnswersData = userAnswersDoc.data();
    const answers = userAnswersData.answers || [];

    // Hitung jumlah jawaban yang benar
    const correctAnswers = answers.filter(answer => answer.isCorrect).length;

    console.log(`Correct answers for user ID ${id}: ${correctAnswers}`);

    // Mengirimkan hasil atau skor
    res.status(200).json({
      success: true,
      userId: id,
      correctAnswers,
      totalAnswers: answers.length,
      score: (correctAnswers / answers.length) * 100, // Skor dalam persentase
      instructions: answers
        .filter(answer => !answer.isCorrect) // Ambil hanya jawaban yang salah
        .map(answer => answer.instruction) // Ambil instruction dari jawaban
        .filter(Boolean) // Hapus nilai null
    });
  } catch (error) {
    console.error('Error fetching score:', error);
    res.status(500).json({ message: 'Error fetching score: ' + error.message });
  }
}
