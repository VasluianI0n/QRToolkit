package com.divergentapp.qrtoolkit.features.history.state

import com.divergentapp.qrtoolkit.core.mvi.BaseScreenState
import com.divergentapp.qrtoolkit.domain.model.QRHistory

data class HistoryState(
    override val isLoading: Boolean = false,
    override val error: String? = null,

    val sections: List<HistorySection> = emptyList(),

    val searchQuery: String = "",

    val selectedItem: QRHistory? = null,

    val showingFavoritesOnly: Boolean = false
) : BaseScreenState(
    isLoading,
    error
)