const express = require('express');
const { getMateri } = require('../controllers/materiController');

const router = express.Router();

router.get('/', getMateri); // Endpoint untuk mendapatkan data materi

module.exports = router;
