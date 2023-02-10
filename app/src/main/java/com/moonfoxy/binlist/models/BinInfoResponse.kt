package com.moonfoxy.binlist.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BinInfoResponse(
    @SerializedName("number") var number: BinNumber? = BinNumber(),
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("prepaid") var prepaid: Boolean? = null,
    @SerializedName("country") var country: BinCountry? = BinCountry(),
    @SerializedName("bank") var bank: BinBank? = BinBank()
) : Parcelable
