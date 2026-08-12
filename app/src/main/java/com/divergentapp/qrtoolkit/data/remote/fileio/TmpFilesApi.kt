package com.divergentapp.qrtoolkit.data.remote.fileio

import com.divergentapp.qrtoolkit.data.remote.TmpFilesResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TmpFilesApi {

    @Multipart
    @POST("api/v1/upload")
    suspend fun upload(
        @Part file: MultipartBody.Part
    ): TmpFilesResponse

}