package com.example.pokeapp.Services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APICall {
    private const val URL_HOST = "https://pokeapi.co/api/v2/"
    const val URL_IMAGE = "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/"

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(URL_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}