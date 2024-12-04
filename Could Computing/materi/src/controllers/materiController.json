const csv = require('csv-parser');
const { readCsvFromGCS } = require('../utils/googleCloudStorage');

exports.getMateri = (req, res) => {
  const results = [];
  const fileName = 'materi/materi_fix.csv'; // Nama file CSV di bucket

  readCsvFromGCS(fileName)
    .pipe(csv())
    .on('data', (data) => {
      // Menambahkan data ke dalam results tanpa mengubah URL gambar
      results.push({
        bagian: data.bagian,
        judul: data.judul,
        isi: data.isi,
        gambar_materi: data.gambar_materi,
        gambar_display: data.gambar_display
      });
    })
    .on('end', () => res.status(200).json(results))  // Kirimkan hasil sebagai response JSON
    .on('error', (err) => {
      console.error('Error membaca file:', err);
      res.status(500).send('Gagal membaca file CSV');
    });
};
