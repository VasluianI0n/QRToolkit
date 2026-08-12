package com.divergentapp.qrtoolkit.features.history.intent

import com.divergentapp.qrtoolkit.core.mvi.UiIntent
import com.divergentapp.qrtoolkit.domain.model.QRHistory

sealed interface HistoryIntent : UiIntent {

    data object Load : HistoryIntent

    data class Search(
        val query: String
    ) : HistoryIntent

    data object ToggleFavoritesFilter : HistoryIntent

    data class Open(
        val item: QRHistory
    ) : HistoryIntent

    data object DismissDetails : HistoryIntent

    data class Delete(
        val id: Long
    ) : HistoryIntent

    data object DeleteAll : HistoryIntent

    data class ToggleFavorite(
        val item: QRHistory
    ) : HistoryIntent

    data class Copy(
        val item: QRHistory
    ) : HistoryIntent

    data class Share(
        val item: QRHistory
    ) : HistoryIntent

    data class OpenContent(
        val item: QRHistory
    ) : HistoryIntent
}