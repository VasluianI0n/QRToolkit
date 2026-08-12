package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.repository.FileUploadRepository
import com.divergentapp.qrtoolkit.features.generator.model.FileUploadResult
import java.io.File

class UploadFileUseCase(
    private val repository: FileUploadRepository
) {

    suspend operator fun invoke(
        file: File,
        mimeType: String,
        onProgress: (Int) -> Unit = {}
    ): Resource<FileUploadResult> =
        repository.upload(file, mimeType, onProgress)

}