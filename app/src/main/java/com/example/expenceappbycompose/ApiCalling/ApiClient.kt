package com.example.expenceappbycompose.ApiCalling
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    public var RETROFIT : Retrofit? = null
    var okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    var gson: Gson = GsonBuilder().create()
    fun getClient(): Retrofit{
            return RETROFIT.let{
                Retrofit.Builder()
                    .baseUrl("https://castigatory-hands.000webhostapp.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
    }
}
