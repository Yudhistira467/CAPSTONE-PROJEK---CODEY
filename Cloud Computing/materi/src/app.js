const express = require('express');
const materiRoutes = require('./routes/materiRoutes');

const app = express();

// Middleware
app.use(express.json());

// Routes
app.use('/materi', materiRoutes);

// Handler untuk root URL '/'
app.get('/', (req, res) => {
  res.send('Selamat datang di API Materi!');
});

// Jalankan server
const PORT = process.env.PORT || 8000;
app.listen(PORT, () => {
  console.log(`Server berjalan di http://localhost:${PORT}`);
});
