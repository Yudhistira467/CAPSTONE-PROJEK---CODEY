from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np

app = Flask(__name__)

# Memuat model TensorFlow
model = tf.keras.models.load_model('best_model.h5')

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Validasi input
        if not request.json or 'input' not in request.json:
            return jsonify({'error': 'Invalid input. JSON with "input" key is required.'}), 400

        # Mendapatkan data input
        data = request.json['input']

        # Konversi data ke numpy array
        data = np.array(data, dtype=np.float32)

        # Prediksi menggunakan model
        prediction = model.predict(data)

        # Mengubah hasil prediksi menjadi list untuk JSON
        return jsonify({'prediction': prediction.tolist()})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
