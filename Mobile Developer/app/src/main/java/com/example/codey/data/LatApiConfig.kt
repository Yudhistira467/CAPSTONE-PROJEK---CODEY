package com.example.codey.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LatApiConfig {
    private const val BASE_URL = "https://latihan-api-291324700318.asia-southeast2.run.app/"

    fun getLatApiService(): LatApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(LatApiService::class.java)
    }
}