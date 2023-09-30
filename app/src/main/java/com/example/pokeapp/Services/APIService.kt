package com.example.pokeapp.Services

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("pokemon?limit=151")
    fun getPokemonList(): Call<PokemonResponse>
}