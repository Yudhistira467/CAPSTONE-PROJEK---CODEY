package com.example.codey.data

import com.example.codey.ui.latihan.LatihanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LatApiService {
    @GET("api/soal")
    fun getSoal(): Call<LatihanResponse>

    @POST("api/jawab")
    fun submitAnswer(
        @Query("question_id") questionId: String,
        @Query("answer") answer: String
    ): Call<Void>

    @GET("api/rekomendasi/get-recommendation")
    fun getRecommendation(): Call<List<String>>

}