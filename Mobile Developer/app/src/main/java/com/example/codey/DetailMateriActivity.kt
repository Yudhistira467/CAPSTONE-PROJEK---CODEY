package com.example.codey

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.codey.R
import com.example.codey.ui.materi.DataMateri

class DetailMateriActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)

        val materi = intent.getParcelableExtra<DataMateri>("EXTRA_MATERI")

        findViewById<TextView>(R.id.tv_detail_judul).text = materi?.judul
        findViewById<TextView>(R.id.tv_detail_isi).text = materi?.isi
        Glide.with(this).load(materi?.gambar_materi).into(findViewById(R.id.iv_detail_gambar))
    }
}