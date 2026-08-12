package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.core.ui.icons.image

@Composable
fun GenerateImageForm(
    fileName: String,
    isUploading: Boolean,
    uploadProgress: Int,
    onPickImage: () -> Unit
) {
    GenerateUploadForm(
        fileName = fileName,
        isUploading = isUploading,
        uploadProgress = uploadProgress,
        chooseText = "Choose Image",
        chooseAnotherText = "Choose another Image",
        fileIcon = image,
        onPickFile = onPickImage
    )
}