package com.example.pokeapp.Services

import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object APICall {
    private const val URL_HOST = "https://pokeapi.co/api/v2/"
    const val URL_IMAGE = "https://unpkg.com/pokeapi-sprites@2.0.2/sprites/pokemon/other/dream-world/"

    fun loadImageFromUrl(imageUrl: String, imageView: SVGImageView) {
        Thread {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream: InputStream = connection.inputStream
                val svg = SVG.getFromInputStream(inputStream)

                imageView.post {
                    imageView.setSVG(svg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(URL_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}