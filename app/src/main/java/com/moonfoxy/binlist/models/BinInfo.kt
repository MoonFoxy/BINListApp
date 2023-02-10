package com.moonfoxy.binlist.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BinInfo(
    @SerializedName("bin") var bin: String? = null,
    @SerializedName("info") var info: BinInfoResponse? = BinInfoResponse(),
    ) : Parcelable
