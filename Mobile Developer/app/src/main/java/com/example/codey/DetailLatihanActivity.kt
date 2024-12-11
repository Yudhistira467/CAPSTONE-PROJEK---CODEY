package com.example.codey

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codey.data.LatApiConfig
import com.example.codey.ui.latihan.LatihanResponse
import com.example.codey.ui.latihan.QuizItem
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
            val jawaban = etJawaban.text.toString()

            submitAnswer(currentSoalIndex, jawaban)

            currentSoalIndex++
            if (currentSoalIndex < soalList.size) {
                displaySoal(currentSoalIndex)
            } else {
                Toast.makeText(this, "Selamat! Kamu sudah menyelesaikan semua soal.", Toast.LENGTH_SHORT).show()
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
        val soal = soalList[index]

        tvInstruction.text = soal.instruction ?: "Tidak ada instruksi"

        tvSoal.text = soal.question ?: "Tidak ada pertanyaan"
        etJawaban.text.clear()
    }

    private fun submitAnswer(index: Int, jawaban: String) {
        val questionId = soalList[index].questionId
        if (questionId != null) {
            LatApiConfig.getLatApiService().submitAnswer(questionId, jawaban).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailLatihanActivity, "Jawaban berhasil dikirim.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailLatihanActivity, "Gagal mengirim jawaban.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@DetailLatihanActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "ID pertanyaan tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }
}