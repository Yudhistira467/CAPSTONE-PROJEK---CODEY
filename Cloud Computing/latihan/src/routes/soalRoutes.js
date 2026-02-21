import express from 'express';
import { getSoal } from '../controllers/soalController.js';

const router = express.Router();

router.get('/', getSoal);

export default router;