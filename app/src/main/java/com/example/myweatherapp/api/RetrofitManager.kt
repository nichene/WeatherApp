package com.example.myweatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    // tudo que tiver dentro do bloco object, é instancia unica.
    // eh estatico, e só pode acessar esse metodo getWeatherService

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getWeatherService() = retrofitInstance.create(WeatherService::class.java)

}