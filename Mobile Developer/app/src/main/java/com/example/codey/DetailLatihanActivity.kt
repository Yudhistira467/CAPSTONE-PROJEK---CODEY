package com.example.codey

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codey.data.LatApiConfig
import com.example.codey.ui.latihan.LatihanResponse
import com.example.codey.ui.latihan.QuizItem
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLatihanActivity : AppCompatActivity() {

    private lateinit var tvSoal: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var etJawaban: EditText
    private lateinit var btnNext: Button
    private var currentSoalIndex = 0
    private var soalList: List<QuizItem> = listOf()
    private val answersList: MutableList<JsonObject> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_latihan)

        tvSoal = findViewById(R.id.tv_soal)
        tvInstruction = findViewById(R.id.tv_instruction)
        etJawaban = findViewById(R.id.et_jawaban)
        btnNext = findViewById(R.id.btn_next)

        val quizId = intent.getStringExtra("quiz_id")

        fetchSoal(quizId)

        btnNext.setOnClickListener {
            if (currentSoalIndex < soalList.size) {
                val jawaban = etJawaban.text.toString()
                val soal = soalList[currentSoalIndex]

                val answerObject = JsonObject().apply {
                    addProperty("questionId", soal.questionId)
                    addProperty("userAnswer", jawaban)
                    addProperty("materi", soal.materi ?: "Unknown")
                }
                answersList.add(answerObject)

                currentSoalIndex++
                if (currentSoalIndex < soalList.size) {
                    displaySoal(currentSoalIndex)
                } else {
                    submitAllAnswers()
                }
            } else {
                Toast.makeText(this, "Tidak ada soal lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchSoal(quizId: String?) {
        LatApiConfig.getLatApiService().getSoal().enqueue(object : Callback<LatihanResponse> {
            override fun onResponse(call: Call<LatihanResponse>, response: Response<LatihanResponse>) {
                if (response.isSuccessful) {
                    soalList = response.body()?.quiz?.filterNotNull() ?: listOf()
                    if (soalList.isNotEmpty()) {
                        displaySoal(currentSoalIndex)
                    } else {
                        Toast.makeText(this@DetailLatihanActivity, "Tidak ada soal yang tersedia.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DetailLatihanActivity, "Gagal mengambil soal.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LatihanResponse>, t: Throwable) {
                Toast.makeText(this@DetailLatihanActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displaySoal(index: Int) {
        if (index in 0 until soalList.size) {
            val soal = soalList[index]

            tvInstruction.text = soal.instruction ?: "Tidak ada instruksi"
            tvSoal.text = soal.question ?: "Tidak ada pertanyaan"
            etJawaban.text.clear()
        } else {
            Toast.makeText(this, "Indeks soal tidak valid.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitAllAnswers() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("LOGGED_IN_USER_ID", null)
        val username = sharedPreferences.getString("LOGGED_IN_USERNAME", null)

        if (userId.isNullOrEmpty() || username.isNullOrEmpty()) {
            Toast.makeText(this, "User belum login, tidak dapat mengirim jawaban.", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody = JsonObject().apply {
            addProperty("id", userId)
            addProperty("username", username)
            add("answers", JsonArray().apply { answersList.forEach { add(it) } })
        }

        LatApiConfig.getLatApiService().submitAnswer(requestBody)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailLatihanActivity, "Semua jawaban berhasil dikirim.", Toast.LENGTH_SHORT).show()
                        fetchScoreAndNavigate(userId)
                    } else {
                        Toast.makeText(this@DetailLatihanActivity, "Gagal mengirim jawaban.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailLatihanActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchScoreAndNavigate(userId: String) {
        LatApiConfig.getLatApiService().getScore(userId).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val scoreData = response.body()
                    val totalAnswers = scoreData?.get("totalAnswers")?.asInt ?: 0
                    val correctAnswers = scoreData?.get("correctAnswers")?.asInt ?: 0
                    val score = scoreData?.get("score")?.asDouble ?: 0.0

                    val intent = Intent(this@DetailLatihanActivity, ResultLatihanActivity::class.java).apply {
                        putExtra("total_questions", totalAnswers)
                        putExtra("correct_answers", correctAnswers)
                        putExtra("score", score)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@DetailLatihanActivity, "Gagal mendapatkan skor.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@DetailLatihanActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
