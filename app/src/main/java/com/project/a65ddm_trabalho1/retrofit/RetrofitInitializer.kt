package com.project.a65ddm_trabalho1.retrofit

import com.project.a65ddm_trabalho1.repository.ApiService
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitInitializer {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:3000/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}