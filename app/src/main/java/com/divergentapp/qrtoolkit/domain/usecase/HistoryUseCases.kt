package com.divergentapp.qrtoolkit.domain.usecase

data class HistoryUseCases(
    val getHistory: GetHistoryUseCase,
    val deleteHistory: DeleteHistoryUseCase,
    val clearHistory: ClearHistoryUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val getSettings: GetSettingsUseCase
)
