package com.example.myweatherapp.entity

data class FindResult(
    val list: List<City>
)

data class City(
    val id: Int,
    val name: String

)