package com.example.codey.ui.latihan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.codey.DetailLatihanActivity
import com.example.codey.R
import com.example.codey.data.LatApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatihanFragment : Fragment() {

    private lateinit var tvJudulLatihan: TextView
    private lateinit var cardViewLatihan: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_latihan, container, false)

        tvJudulLatihan = view.findViewById(R.id.tv_judul_latihan)
        cardViewLatihan = view.findViewById(R.id.card_view_latihan)

        fetchQuizTitle()

        cardViewLatihan.setOnClickListener {
            val quizId = "example_quiz_id"

            val intent = Intent(requireContext(), DetailLatihanActivity::class.java)
            intent.putExtra("quiz_id", quizId)
            startActivity(intent)
        }

        return view
    }

    private fun fetchQuizTitle() {
        LatApiConfig.getLatApiService().getSoal().enqueue(object : Callback<LatihanResponse> {
            override fun onResponse(call: Call<LatihanResponse>, response: Response<LatihanResponse>) {
                if (response.isSuccessful) {
                    val quizTitle = response.body()?.quiz?.firstOrNull()?.materi
                    if (quizTitle != null) {
                        tvJudulLatihan.text = quizTitle
                    } else {
                        tvJudulLatihan.text = "Tidak ada judul latihan"
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LatihanResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}