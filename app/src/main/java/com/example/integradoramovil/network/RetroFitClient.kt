package com.example.integradoramovil.network


import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient {
    val url = "https://transorbital-crowingly-brittney.ngrok-free.dev/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: apiservice = retrofit.create(apiservice::class.java)
}

