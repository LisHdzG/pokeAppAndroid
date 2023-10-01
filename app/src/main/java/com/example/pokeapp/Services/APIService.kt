package com.example.pokeapp.Services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("pokemon?limit=151")
    fun getPokemonList(): Call<PokemonResponse>

    @GET("pokemon/{id}/")
    fun getPokemonDetail(@Path("id") id: Int): Call<PokemonDetailResponse>
}