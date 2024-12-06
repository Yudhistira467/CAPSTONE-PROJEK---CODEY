package com.example.codey.ui.materi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMateri(
    val judul: String,
    val isi: String,
    val gambar_materi: String,
    val gambar_display: String
) : Parcelable
