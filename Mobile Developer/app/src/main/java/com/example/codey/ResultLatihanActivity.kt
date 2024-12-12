package com.example.codey

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.codey.data.LatApiConfig
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultLatihanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_latihan)

        val userId = intent.getStringExtra("user_id") ?: ""
        val totalQuestions = intent.getIntExtra("total_questions", 0)
        val correctAnswers = intent.getIntExtra("correct_answers", 0)
        val score = intent.getDoubleExtra("score", 0.0)

        val tvResultDetail = findViewById<TextView>(R.id.tv_result_detail)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val tvRecommendation = findViewById<TextView>(R.id.tv_recommendation)
        val btnBackHome = findViewById<Button>(R.id.btn_back_home)

        tvResultDetail.text = "Kamu menjawab $correctAnswers dari $totalQuestions soal dengan benar."
        tvScore.text = "Skor Kamu: ${String.format("%.2f", score)}"

        if (userId.isNotEmpty()) {
            fetchScore(userId, tvRecommendation)
        } else {
            tvRecommendation.text = "User ID tidak ditemukan."
        }

        btnBackHome.setOnClickListener {
            finish()
        }
    }

    private fun fetchScore(userId: String, tvRecommendation: TextView) {
        LatApiConfig.getLatApiService().getRecommendation(userId).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val recommendation = responseBody.get("recommendation")?.asString

                        Log.d("ResultLatihan", "Recommendation: $recommendation")

                        tvRecommendation.text = recommendation ?: "Tidak ada rekomendasi."
                    } else {
                        tvRecommendation.text = "Gagal mendapatkan rekomendasi."
                        Log.e("ResultLatihan", "Response body is null")
                    }
                } else {
                    tvRecommendation.text = "Gagal mendapatkan data rekomendasi."
                    Log.e("ResultLatihan", "Response failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                tvRecommendation.text = "Terjadi kesalahan: ${t.message}"
                Log.e("ResultLatihan", "API call failed: ${t.message}", t)
            }
        })
    }


}