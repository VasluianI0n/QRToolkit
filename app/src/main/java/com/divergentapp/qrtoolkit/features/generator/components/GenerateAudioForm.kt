package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.core.ui.icons.audio_file

@Composable
fun GenerateAudioForm(
    fileName: String,
    isUploading: Boolean,
    uploadProgress: Int,
    onPickAudio: () -> Unit
) {
    GenerateUploadForm(
        fileName = fileName,
        isUploading = isUploading,
        uploadProgress = uploadProgress,
        chooseText = "Choose Audio",
        chooseAnotherText = "Choose another Audio",
        fileIcon = audio_file,
        onPickFile = onPickAudio
    )
}