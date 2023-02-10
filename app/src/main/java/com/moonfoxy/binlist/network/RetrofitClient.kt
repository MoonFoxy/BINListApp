package com.moonfoxy.binlist.network

import com.google.gson.GsonBuilder
import com.moonfoxy.binlist.interfaces.RetrofitInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val gson = GsonBuilder().setLenient().create()

    val retrofit: RetrofitInterface by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BinListApi.BASE_URL)
            .build().create(RetrofitInterface::class.java)
    }
}