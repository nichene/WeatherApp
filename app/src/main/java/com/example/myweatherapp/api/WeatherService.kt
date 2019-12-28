package com.example.myweatherapp.api

import com.example.myweatherapp.entity.City
import com.example.myweatherapp.entity.FindResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded



interface WeatherService {

    @GET("find?")
    fun find(
        @Query("q")
        cityName: String,
        @Query("lang")
        lang: String,
        @Query("units")
        unit: String,
        @Query("appid")
        appId: String
    ) : Call <FindResult>



    @GET("group?")
    fun getPreferred(
        @Query ("id")
        cities : String,
        @Query("appid")
        appId: String
    ): Call<FindResult>



}