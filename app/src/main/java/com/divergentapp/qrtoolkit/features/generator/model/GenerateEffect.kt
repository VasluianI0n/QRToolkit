package com.divergentapp.qrtoolkit.features.generator.model

import com.divergentapp.qrtoolkit.core.mvi.UiEffect

sealed interface GenerateEffect : UiEffect {
    data class ShowMessage(
        val message: String
    ) : GenerateEffect
}