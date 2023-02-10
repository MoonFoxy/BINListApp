package com.moonfoxy.binlist.interfaces

import com.moonfoxy.binlist.models.BinInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {
    @GET("{bin}")
    fun getDataList(@Path("bin") bin: String): Call<BinInfoResponse>
}