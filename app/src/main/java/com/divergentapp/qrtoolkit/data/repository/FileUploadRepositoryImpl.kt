package com.divergentapp.qrtoolkit.data.repository

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.data.remote.ProgressRequestBody
import com.divergentapp.qrtoolkit.data.remote.fileio.TmpFilesApi
import com.divergentapp.qrtoolkit.domain.repository.FileUploadRepository
import com.divergentapp.qrtoolkit.features.generator.model.FileUploadResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileUploadRepositoryImpl(
    private val api: TmpFilesApi
) : FileUploadRepository {
    override suspend fun upload(
        file: File,
        mimeType: String,
        onProgress: (Int) -> Unit
    ): Resource<FileUploadResult> {

        return try {

            val requestBody = ProgressRequestBody(
                file = file,
                contentType = mimeType.toMediaType(),
                listener = onProgress
            )

            val part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestBody
            )

            val response = api.upload(part)

            if (response.status != "success") {
                return Resource.Error(message = "Upload failed.")
            }

            Resource.Success(
                FileUploadResult(
                    url = response.data.url,
                    fileName = file.name
                )
            )

        } catch (e: Exception) {
            Resource.Error(message = e.message)
        }
    }

}