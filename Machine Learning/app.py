from flask import Flask, request, jsonify
import pandas as pd
from google.cloud import storage  # Pastikan library ini diinstal

def load_data_from_gcs(bucket_name, file_name):
    """
    Mengunduh file CSV dari Google Cloud Storage dan mengonversinya menjadi DataFrame.
    """
    client = storage.Client()
    bucket = client.bucket(bucket_name)
    blob = bucket.blob(file_name)

    # Unduh isi file sebagai string
    data = blob.download_as_text()

    # Konversi isi file ke DataFrame
    df = pd.read_csv(pd.compat.StringIO(data))
    return df


# Ambil data dari GCS
BUCKET_NAME = "nama_bucket_anda"  # Ganti dengan nama bucket Anda
BLOB_NAME = "path/to/questions.csv"  # Ganti dengan path file di bucket
data = load_data_from_gcs(BUCKET_NAME, BLOB_NAME)
data = data[['question_id', 'material']]

@app.route('/')
def home():
    return """
    <h1>Python Learning Material Recommendation System</h1>
    <p>Material recommendations based on user interaction data with questions.</p>
    <form action="/recommend" method="post">
        <label for="user_id">User ID:</label><br>
        <input type="number" id="user_id" name="user_id" required><br><br>

        <label for="question_ids">Question IDs (comma-separated):</label><br>
        <input type="text" id="question_ids" name="question_ids" required><br><br>

        <label for="attempts">Number of Attempts (comma-separated):</label><br>
        <input type="text" id="attempts" name="attempts" required><br><br>

        <label for="corrects">True or False (0 for false, 1 for true, comma-separated):</label><br>
        <input type="text" id="corrects" name="corrects" required><br><br>

        <label for="durations">Duration of answering (in seconds, comma-separated):</label><br>
        <input type="text" id="durations" name="durations" required><br><br>

        <button type="submit">Process and Recommend</button>
    </form>
    """

@app.route('/recommend', methods=['POST'])
def recommend():
    try:
        # Ambil nama bucket dan file dari request (atau hardcoded jika tidak dinamis)
        bucket_name = request.form.get('bucket_name', 'nama_bucket_anda')
        file_name = request.form.get('file_name', 'path/to/user_interaction.csv')

        # Unduh data dari Google Cloud Storage
        user_interaction_df = load_data_from_gcs(bucket_name, file_name)

        # Gabungkan data interaksi pengguna dengan data materi berdasarkan question_id
        user_interaction_df = user_interaction_df.merge(data, on='question_id', how='left')

        # Hitung rata-rata kesalahan per materi
        user_error_data = user_interaction_df.groupby('material').agg({
            'attempts': 'mean',
            'correct': 'mean',
            'duration': 'mean'
        }).reset_index()

        # Tambahkan kolom tingkat kesalahan
        user_error_data['avg_error_rate'] = 1 - user_error_data['correct']

        # Rekomendasikan materi dengan tingkat kesalahan tertinggi
        user_recommended_materials = user_error_data.sort_values(by='avg_error_rate', ascending=False).head(1)

        # Jika rekomendasi tersedia
        if not user_recommended_materials.empty:
            result = {
                "recommended_material": user_recommended_materials['material'].iloc[0],
                "avg_error_rate": user_recommended_materials['avg_error_rate'].iloc[0]
            }
        else:
            result = {"message": "Data is insufficient to provide recommendations."}

        return jsonify(result)

    except Exception as e:
        return jsonify({"error": f"An error occurred: {e}"})


if __name__ == '__main__':
    app.run(debug=True)
