package com.divergentapp.qrtoolkit.features.generator.model

import com.divergentapp.qrtoolkit.core.mvi.UiIntent
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.features.generator.components.GenerateType
import java.io.File

sealed interface GenerateIntent : UiIntent {

    data class SelectType(
        val type: GenerateType
    ) : GenerateIntent

    data class UpdateContent(
        val content: QRContent
    ) : GenerateIntent

    data class FileSelected(
        val file: File,
        val mimeType: String
    ) : GenerateIntent

    data object Generate : GenerateIntent
    data object DismissPreview : GenerateIntent
}