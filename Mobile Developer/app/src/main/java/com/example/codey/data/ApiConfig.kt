package com.example.codey.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getMateriApiService(): MateriApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://backend-dot-capstone-3131.et.r.appspot.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MateriApiService::class.java)
    }
}