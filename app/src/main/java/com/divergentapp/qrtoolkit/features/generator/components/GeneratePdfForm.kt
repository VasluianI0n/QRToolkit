package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.core.ui.icons.picture_as_pdf

@Composable
fun GeneratePdfForm(
    fileName: String,
    isUploading: Boolean,
    uploadProgress: Int,
    onPickPdf: () -> Unit
) {
    GenerateUploadForm(
        fileName = fileName,
        isUploading = isUploading,
        uploadProgress = uploadProgress,
        chooseText = "Choose PDF",
        chooseAnotherText = "Choose another PDF",
        fileIcon = picture_as_pdf,
        onPickFile = onPickPdf
    )
}