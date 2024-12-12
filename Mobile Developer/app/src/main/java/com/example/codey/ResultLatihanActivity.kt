package com.example.codey

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultLatihanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_latihan)

        val totalQuestions = intent.getIntExtra("total_questions", 0)
        val correctAnswers = intent.getIntExtra("correct_answers", 0)
        val score = intent.getDoubleExtra("score", 0.0)

        val tvResultDetail = findViewById<TextView>(R.id.tv_result_detail)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val btnBackHome = findViewById<Button>(R.id.btn_back_home)

        //tambahin rekomendasi disini

        tvResultDetail.text = "Kamu menjawab $correctAnswers dari $totalQuestions soal dengan benar."
        tvScore.text = "Skor Kamu: ${String.format("%.2f", score)}"

        btnBackHome.setOnClickListener {
            finish()
        }
    }
}