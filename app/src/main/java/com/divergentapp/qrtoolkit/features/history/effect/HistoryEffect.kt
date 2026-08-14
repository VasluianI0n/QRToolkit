package com.divergentapp.qrtoolkit.features.history.effect

import com.divergentapp.qrtoolkit.core.mvi.UiEffect
import com.divergentapp.qrtoolkit.domain.model.QRContent

sealed interface HistoryEffect : UiEffect{

    data object ShowInterstitial : HistoryEffect

    data class Open(
        val content: QRContent
    ) : HistoryEffect

    data class Copy(
        val rawValue: String
    ) : HistoryEffect

    data class Share(
        val rawValue: String
    ) : HistoryEffect

    data class ShowMessage(
        val message: String
    ) : HistoryEffect

}