package com.example.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVGImageView
import com.example.pokeapp.Services.APICall
import com.example.pokeapp.Services.APIService
import com.example.pokeapp.Services.PokemonDetailResponse
import com.example.pokeapp.Services.PokemonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPokeomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokeom)

        val idPokemon: Int = intent.getIntExtra("id_pokemon", 0)
        callPokemonDetail(idPokemon)

    }


    private fun callPokemonDetail(id_pokemon:Int){
        val call = APICall.getRetrofit().create(APIService::class.java).getPokemonDetail(id_pokemon)

        CoroutineScope(Dispatchers.IO).launch {
            call.enqueue(object : Callback<PokemonDetailResponse> {
                override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {
                    response.body()?.let { it ->
                        showView(it)
                    } ?: run {
                        println("lisette run")
                    }
                }
                override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                    println("lisette fail")

                }

            })
        }
    }

    fun showView(pokemonResponse: PokemonDetailResponse ){
        val textViewNamePlaceHolder: TextView = findViewById(R.id.textView2)
        val textViewName: TextView = findViewById(R.id.textView3)
        val imageView: SVGImageView = findViewById(R.id.imageView)
        val baseExperience: TextView = findViewById(R.id.base_experience)
        val height: TextView = findViewById(R.id.height)
        val weight: TextView = findViewById(R.id.weight)

        textViewNamePlaceHolder.text = pokemonResponse.name.uppercase()
        textViewName.text = pokemonResponse.name.uppercase()
        baseExperience.text = "Base Experience: " + pokemonResponse.base_experience.toString()
        height.text = "Height: " + pokemonResponse.height.toString() + " m"
        weight.text = "Weight: " + pokemonResponse.weight.toString() + " kg"

        APICall.loadImageFromUrl(APICall.URL_IMAGE + pokemonResponse.id + ".svg", imageView)


    }
}