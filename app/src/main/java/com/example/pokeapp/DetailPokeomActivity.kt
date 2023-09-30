package com.example.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailPokeomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokeom)

        val id_pokemon: Int = intent.getIntExtra("id_pokemon", 0)

    }
}