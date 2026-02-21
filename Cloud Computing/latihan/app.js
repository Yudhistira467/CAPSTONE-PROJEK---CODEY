import express from 'express';
import bodyParser from 'body-parser';
import soalRoutes from './src/routes/soalRoutes.js';
import { saveAnswers, getScore } from './src/controllers/jawabController.js';
import rekomendasiRoutes from './src/routes/rekomendasiRoutes.js';

const app = express();
const PORT = 8080;

// Middleware
app.use(bodyParser.json());

// Routes
app.use('/api/soal', soalRoutes);
app.post('/api/jawab', saveAnswers);
app.get('/api/score', getScore);
app.use('/api/rekomendasi', rekomendasiRoutes);

// Start Server
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
