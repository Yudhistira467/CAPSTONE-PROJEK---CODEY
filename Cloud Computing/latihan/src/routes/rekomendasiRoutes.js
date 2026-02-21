import express from 'express';
import axios from 'axios';

const router = express.Router();

// Endpoint untuk mendapatkan rekomendasi
router.get('/get-recommendation', async (req, res) => {
  try {
    const userId = req.query.userId;
    if (!userId) {
      return res.status(400).json({ error: 'userId parameter is required' });
    }

    // URL API dari Flask server Anda yang di-deploy di Cloud Run
    const apiUrl = `https://rekomendasi-291324700318.asia-southeast2.run.app/get-recommendation?userId=${userId}`;

    // Panggil API menggunakan axios
    const response = await axios.get(apiUrl);
    res.json(response.data);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: error.message });
  }
});

// Ekspor router sebagai default
export default router;