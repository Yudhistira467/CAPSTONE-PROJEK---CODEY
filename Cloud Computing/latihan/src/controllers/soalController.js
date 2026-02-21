import { Storage } from '@google-cloud/storage';
import serviceAccount from '../config/serviceAccountKey.json' assert { type: 'json' };

// Inisialisasi Google Cloud Storage menggunakan Service Account langsung
const storage = new Storage({
  projectId: serviceAccount.project_id,
  credentials: {
    client_email: serviceAccount.client_email,
    private_key: serviceAccount.private_key,
  },
});

// Nama bucket yang digunakan
const bucketName = 'edu-app-python-material'; // Ganti dengan nama bucket Anda

// Fungsi untuk mendapatkan soal dari Google Cloud Storage
export async function getSoal(req, res) {
  try {
    const bucket = storage.bucket(bucketName);
    const file = bucket.file('question/gabungan.json');

    // Periksa apakah file ada di bucket
    const [exists] = await file.exists();
    if (!exists) {
      return res.status(404).json({ message: 'File gabungan.json tidak ditemukan di bucket.' });
    }

    // Mengunduh isi file soal
    const [contents] = await file.download();
    const soal = JSON.parse(contents.toString());

    // Mengembalikan data soal
    res.json(soal);
  } catch (error) {
    // Menangani error dengan memberikan informasi yang jelas
    console.error('Error fetching soal:', error);
    res.status(500).json({ message: 'Terjadi kesalahan saat mengambil data soal.', error: error.message });
  }
}
