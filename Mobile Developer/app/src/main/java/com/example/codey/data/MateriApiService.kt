package com.example.codey.data

import com.example.codey.ui.materi.DataMateri
import retrofit2.http.GET

interface MateriApiService {
    @GET("/materi")
    suspend fun getMateri(): List<DataMateri>
}