package com.example.pokeapp

import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVG
import com.example.pokeapp.Services.APICall
import com.example.pokeapp.Services.APIService
import com.example.pokeapp.Services.Pokemon
import com.example.pokeapp.Services.PokemonResponse
import com.example.pokeapp.Services.Recyclers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var recyclerList = mutableListOf<Recyclers>()

    fun updateList(pokemonList: MutableList<Pokemon>, adapter: ItemAdapter, startIndex: Int){
        adapter.updateData(pokemonList, startIndex)
    }

    private lateinit var textViewNumberPokemons: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewFirstLine: RecyclerView = findViewById(R.id.recycler_uno)
        val recyclerViewSecondLine: RecyclerView = findViewById(R.id.recycler_dos)
        val recyclerViewThirdLine: RecyclerView = findViewById(R.id.recycler_tres)
        textViewNumberPokemons = findViewById(R.id.textView)

        recyclerList.add(Recyclers(recyclerViewFirstLine, ItemAdapter( mutableListOf(), this)))
        recyclerList.add(Recyclers(recyclerViewSecondLine, ItemAdapter( mutableListOf(), this)))
        recyclerList.add(Recyclers(recyclerViewThirdLine, ItemAdapter( mutableListOf(), this)))

        for (recycler in recyclerList){
            recycler.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycler.recycler.adapter = recycler.adapter
        }

        callPokemon()
    }

    private fun callPokemon(){
        val call = APICall.getRetrofit().create(APIService::class.java).getPokemonList()

        CoroutineScope(Dispatchers.IO).launch {
            call.enqueue(object : Callback<PokemonResponse> {
                override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                    response.body()?.let { it ->
                        val sizePatition = it.results.size / 3
                        updateList( it.results.subList(0, sizePatition), recyclerList[0].adapter, 0)
                        updateList( it.results.subList(sizePatition, 2*sizePatition), recyclerList[1].adapter, sizePatition)
                        updateList( it.results.subList(2*sizePatition, it.results.size), recyclerList[2].adapter, 2*sizePatition)
                        textViewNumberPokemons.text = (it.results.size).toString() + " Pokémones in your Pokédex"
                    } ?: run {
                        println("lisette run")
                    }
                }
                override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                    println("lisette fail")

                }

            })
        }
    }
}