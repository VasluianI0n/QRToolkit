package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.core.ui.icons.video_library

@Composable
fun GenerateVideoForm(
    fileName: String,
    isUploading: Boolean,
    uploadProgress: Int,
    onPickVideo: () -> Unit
) {
    GenerateUploadForm(
        fileName = fileName,
        isUploading = isUploading,
        uploadProgress = uploadProgress,
        chooseText = "Choose Video",
        chooseAnotherText = "Choose another Video",
        fileIcon = video_library,
        onPickFile = onPickVideo
    )
}