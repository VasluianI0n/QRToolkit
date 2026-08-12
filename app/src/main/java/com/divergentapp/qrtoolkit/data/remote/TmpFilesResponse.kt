package com.divergentapp.qrtoolkit.data.remote

data class TmpFilesResponse(
    val status: String,
    val data: TmpFilesData
)

data class  TmpFilesData(
    val url: String
)