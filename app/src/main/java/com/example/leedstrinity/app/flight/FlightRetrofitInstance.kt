package com.example.leedstrinity.app.flight


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FlightRetrofitInstance {

    private const val BASE_URL = "http://api.aviationstack.com/"

    val api: FlightApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApi::class.java)
    }
}
