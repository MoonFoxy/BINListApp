package com.moonfoxy.binlist.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BinNumber(
    @SerializedName("length") var length: Int? = null,
    @SerializedName("luhn") var luhn: Boolean? = null
) : Parcelable
