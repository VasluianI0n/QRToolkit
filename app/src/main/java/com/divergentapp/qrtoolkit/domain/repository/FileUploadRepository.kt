package com.divergentapp.qrtoolkit.domain.repository

import android.net.Uri
import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.features.generator.model.FileUploadResult
import java.io.File

interface FileUploadRepository {

    suspend fun upload(
        file: File,
        mimeType: String,
        onProgress: (Int) -> Unit = {}
    ): Resource<FileUploadResult>

}