from flask import Flask, jsonify, request
from google.cloud import firestore
import os
import logging
import tensorflow as tf
import numpy as np
import pickle

app = Flask(__name__)

# Konfigurasi Google Cloud Firestore
os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = "key_firestore.json"

logging.basicConfig(level=logging.INFO)

# Nama file lokal untuk model dan tokenizer
MODEL_PATH = "model_codey.h5"
TOKENIZER_PATH = "tokenizer.pkl"

# Fungsi untuk memetakan hasil prediksi ke string
def map_predictions_to_string(predictions):
    labels = [
        "Introduction to Python", "Operators", "Data Types",
        "Data Structures", "Control Structures", "Function",
        "Object-Oriented Programming (OOP)", "Lainnya"
    ]
    predicted_label = labels[np.argmax(predictions)]
    return predicted_label

# Fungsi untuk memuat tokenizer
def load_tokenizer():
    try:
        with open(TOKENIZER_PATH, 'rb') as file:
            tokenizer = pickle.load(file)
        logging.info("Tokenizer loaded successfully.")
        return tokenizer
    except FileNotFoundError:
        logging.error(f"Tokenizer file not found at {TOKENIZER_PATH}")
        return None

# Fungsi pra-pemrosesan teks
def preprocess_text(text, tokenizer, max_length=100):
    # Tokenisasi teks
    sequences = tokenizer.texts_to_sequences([text])
    # Padding agar sesuai dengan panjang input model
    padded = tf.keras.preprocessing.sequence.pad_sequences(sequences, maxlen=max_length, padding='post')
    return padded

@app.route('/get-recommendation', methods=['GET'])
def get_recommendation():
    try:
        # Ambil parameter dari permintaan
        user_id = request.args.get('userId')
        if not user_id:
            return jsonify({"error": "userId parameter is required"}), 400
        
        logging.info(f"Received userId: {user_id}")
        
        # Inisialisasi Firestore
        logging.info("Initializing Firestore client")
        firestore_client = firestore.Client()
        
        # Ambil data dari koleksi 'user_answer' dan dokumen dengan ID 'user_id'
        doc_ref = firestore_client.collection('user_answer').document(user_id)
        doc = doc_ref.get()
        
        if not doc.exists:
            logging.warning(f"No data found for userId: {user_id}")
            return jsonify({"error": "No data found for the given userId"}), 404
        
        # Ambil semua field dari dokumen
        user_data = doc.to_dict()
        logging.info(f"User data retrieved: {user_data}")
        
        # Periksa apakah ada field 'instruction'
        instruction = None
        if 'answers' in user_data:
            for answer in user_data['answers']:
                if 'instruction' in answer and answer['instruction']:
                    instruction = answer['instruction']
                    break
        
        if not instruction:
            logging.warning(f"Instruction is empty or not found in data: {user_data}")
            logging.info(f"All user data fields: {user_data.keys()}")
            return jsonify({"error": "Instruction is empty or not found"}), 400
        
        logging.info(f"Instruction from Firestore: {instruction}")
        
        # Memuat model
        if not os.path.exists(MODEL_PATH):
            return jsonify({"error": f"Model file not found at {MODEL_PATH}"}), 500
        
        logging.info("Loading model")
        model = tf.keras.models.load_model(MODEL_PATH)
        
        # Memuat tokenizer
        tokenizer = load_tokenizer()
        if tokenizer is None:
            return jsonify({"error": "Tokenizer not loaded"}), 500
        
        # Pra-pemrosesan *instruction*
        input_data = preprocess_text(instruction, tokenizer)
        logging.info(f"Input data: {input_data}")
        
        # Prediksi menggunakan model
        predictions = model.predict(input_data)
        logging.info(f"Predictions: {predictions}")
        
        # Pemetaan hasil prediksi ke string
        recommendation = map_predictions_to_string(predictions)
        logging.info(f"Recommendation: {recommendation}")
        
        # Kirim hasil rekomendasi ke client
        return jsonify({"recommendation": recommendation})
    
    except Exception as e:
        logging.error(f"Error: {e}")
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    from waitress import serve
    logging.info("Starting server")
    serve(app, host='0.0.0.0', port=8080)
