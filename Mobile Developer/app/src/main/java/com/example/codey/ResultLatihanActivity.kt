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

        val tvResultDetail = findViewById<TextView>(R.id.tv_result_detail)
        val btnBackHome = findViewById<Button>(R.id.btn_back_home)

        tvResultDetail.text = "Kamu menjawab $correctAnswers dari $totalQuestions soal dengan benar."

        btnBackHome.setOnClickListener {
            finish()
        }
    }
}