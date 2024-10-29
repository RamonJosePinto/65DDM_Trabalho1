package com.project.a65ddm_trabalho1.retrofit

import com.project.a65ddm_trabalho1.repository.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetrofitInitializer {

    val client = OkHttpClient.Builder()
        .readTimeout(120,TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://65-ddm-api.vercel.app/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): ApiService {
        return retrofit.create()
    }
}