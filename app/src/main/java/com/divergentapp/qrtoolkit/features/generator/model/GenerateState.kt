package com.divergentapp.qrtoolkit.features.generator.model

import android.graphics.Bitmap
import com.divergentapp.qrtoolkit.core.mvi.BaseScreenState
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.features.generator.components.GenerateType

data class GenerateState(

    override val isLoading: Boolean = false,

    override val error: String? = null,

    val selectedType: GenerateType = GenerateType.IMAGE,

    val content: QRContent = GenerateType.IMAGE.defaultContent(),

    val bitmap: Bitmap? = null,

    val isUploading: Boolean = false,

    val uploadProgress: Int = 0

) : BaseScreenState(
    isLoading,
    error
)