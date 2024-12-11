import express from 'express';
import { saveAnswer } from '../controllers/jawabController.js';

const router = express.Router();

router.post('/', saveAnswer);

export default router;
