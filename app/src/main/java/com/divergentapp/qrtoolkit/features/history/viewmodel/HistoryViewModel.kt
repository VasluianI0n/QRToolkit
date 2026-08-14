package com.divergentapp.qrtoolkit.features.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.divergentapp.qrtoolkit.core.mvi.BaseViewModel
import com.divergentapp.qrtoolkit.core.ui.extensions.title
import com.divergentapp.qrtoolkit.core.ui.extensions.toHistorySections
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.usecase.HistoryUseCases
import com.divergentapp.qrtoolkit.features.history.effect.HistoryEffect
import com.divergentapp.qrtoolkit.features.history.intent.HistoryIntent
import com.divergentapp.qrtoolkit.features.history.state.HistoryState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    private val useCases: HistoryUseCases
) : BaseViewModel<
        HistoryIntent,
        HistoryState,
        HistoryEffect
        >(HistoryState()) {

    /**
     * Unfiltered history from Room.
     */
    private var allHistory: List<QRHistory> = emptyList()

    val settings = useCases.getSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    init {
        observeHistory()
    }

    override fun onIntent(intent: HistoryIntent) {
        when (intent) {

            is HistoryIntent.Search ->
                updateSearch(intent.query)

            HistoryIntent.ToggleFavoritesFilter ->
                toggleFavorites()

            is HistoryIntent.Open ->
                openItem(intent.item)

            HistoryIntent.DismissDetails ->
                dismissDetails()

            is HistoryIntent.Delete ->
                delete(intent.id)

            HistoryIntent.DeleteAll ->
                clearHistory()

            is HistoryIntent.ToggleFavorite ->
                toggleFavorite(intent.item)

            is HistoryIntent.Copy ->
                sendEffect(
                    HistoryEffect.Copy(intent.item.rawValue)
                )

            is HistoryIntent.Share ->
                sendEffect(
                    HistoryEffect.Share(intent.item.rawValue)
                )

            is HistoryIntent.OpenContent ->
                sendEffect(
                    HistoryEffect.Open(intent.item.content)
                )

            HistoryIntent.Load -> Unit
        }
    }

    private fun observeHistory() {
        launch {
            useCases.getHistory()
                .collectLatest { resource ->
                    handleResource(
                        resource = resource,
                        onSuccess = { history ->
                            allHistory = history
                            applyFilters()
                        }
                    )
                }
        }
    }

    private fun updateSearch(
        query: String
    ) {

        setState {
            copy(
                searchQuery = query
            )
        }

        applyFilters()

    }

    private fun toggleFavorites() {

        setState {

            copy(
                showingFavoritesOnly = !showingFavoritesOnly
            )

        }

        applyFilters()

    }

    private fun applyFilters() {
        val query = state.value.searchQuery.trim()
        val filtered = allHistory
            .filter { item ->
                (!state.value.showingFavoritesOnly || item.isFavorite) &&
                        (query.isBlank() || item.matches(query))
            }
        setState {
            copy(
                sections = filtered.toHistorySections()
            )
        }

    }

    private fun openItem(
        item: QRHistory
    ) {
        setState {
            copy(
                selectedItem = item
            )
        }

        sendEffect(HistoryEffect.ShowInterstitial)
    }

    private fun dismissDetails() {
        setState {
            copy(
                selectedItem = null
            )
        }
    }

    private fun delete(
        id: Long
    ) {
        launch {
            handleResource(
                resource = useCases.deleteHistory(id),
                onSuccess = {
                    sendEffect(
                        HistoryEffect.ShowMessage(
                            "Deleted"
                        )
                    )
                }
            )
        }
    }

    private fun clearHistory() {
        launch {
            handleResource(
                resource = useCases.clearHistory(),
                onSuccess = {
                    sendEffect(
                        HistoryEffect.ShowMessage(
                            "History cleared"
                        )
                    )
                }
            )
        }
    }

    private fun toggleFavorite(
        item: QRHistory
    ) {
        launch {
            handleResource(
                resource = useCases.toggleFavorite(
                    id = item.id,
                    favorite = !item.isFavorite
                ),
                onSuccess = {
                    // No need to manually update the UI.
                    // Room emits the updated list and observeHistory() refreshes it.
                }
            )
        }
    }

    private fun QRHistory.matches(query: String): Boolean {

        if (query.isBlank()) return true

        val q = query.lowercase()

        return rawValue.lowercase().contains(q) ||
                content.title().lowercase().contains(q)
    }
}