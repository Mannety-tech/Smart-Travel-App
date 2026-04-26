package com.example.leedstrinity.app.directions

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitDirections {

    private const val BASE_URL = "https://maps.googleapis.com/"

    val api: DirectionsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectionsApi::class.java)
    }
}

