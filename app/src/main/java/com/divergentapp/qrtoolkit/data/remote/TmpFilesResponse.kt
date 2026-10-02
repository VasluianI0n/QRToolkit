package com.divergentapp.qrtoolkit.data.remote

import com.google.gson.annotations.SerializedName

data class TmpFilesResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: TmpFilesData
)

data class  TmpFilesData(
    @SerializedName("url")
    val url: String
)