const { Storage } = require('@google-cloud/storage');

// Inisialisasi Google Cloud Storage
const storage = new Storage({ keyFilename: 'keys/service-account.json' });

const bucketName = 'edu-app-python-material'; // Nama bucket Anda

// Fungsi untuk membaca file CSV dari GCS
const readCsvFromGCS = (fileName) => {
  return storage.bucket(bucketName).file(fileName).createReadStream();
};

// Fungsi untuk membuat URL gambar berdasarkan nama file
const generateImageUrl = (fileName) => {
  return `https://storage.googleapis.com/${bucketName}/${fileName}`;
};

module.exports = { readCsvFromGCS, generateImageUrl };
