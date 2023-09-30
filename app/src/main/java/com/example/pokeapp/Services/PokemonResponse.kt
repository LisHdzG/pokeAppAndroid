package com.example.pokeapp.Services

data class PokemonResponse(val count: Int, val next: String, val results: MutableList<Pokemon>)
