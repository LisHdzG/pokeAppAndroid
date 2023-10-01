package com.example.pokeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.example.pokeapp.Services.APICall
import com.example.pokeapp.Services.Pokemon
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ItemAdapter (private val items: MutableList<Pokemon>, val context: Context) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var starIndex = 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: SVGImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val idPokemon = (starIndex+position+1)
        holder.imageView.setImageDrawable(context.getDrawable(R.drawable.placeholder))
        APICall.loadImageFromUrl(APICall.URL_IMAGE + idPokemon + ".svg", holder.imageView)
        holder.textView.text = item.name

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailPokeomActivity::class.java)
            intent.putExtra("id_pokemon", idPokemon)
            context.startActivity(intent)

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Pokemon>, starIndex: Int){
        items.clear()
        items.addAll(newItems)
        this.starIndex = starIndex
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return items.size
    }
}