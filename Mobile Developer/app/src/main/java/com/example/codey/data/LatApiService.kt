package com.example.codey.data

import com.example.codey.ui.latihan.LatihanResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LatApiService {
    @GET("api/soal")
    fun getSoal(): Call<LatihanResponse>

    @POST("api/jawab")
    fun submitAnswer(
        @Body body: JsonObject
    ): Call<Void>

    @GET("api/rekomendasi/get-recommendation")
    fun getRecommendation(): Call<List<String>>

}