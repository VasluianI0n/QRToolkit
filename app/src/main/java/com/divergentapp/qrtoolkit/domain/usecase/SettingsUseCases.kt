package com.divergentapp.qrtoolkit.domain.usecase

data class SettingsUseCases(

    val getSettings: GetSettingsUseCase,
    val setDarkTheme: SetDarkThemeUseCase,
    val setUseSystemTheme: SetUseSystemTheme,
    val setVibration: SetVibrationUseCase,
    val setBeep: SetBeepUseCase,
    val setAutoOpenLinks: SetAutoOpenLinksUseCase,
    val setSaveGeneratedHistory: SetSaveGeneratedHistoryUseCase,
    val setSaveScannedHistory: SetSaveScannedHistoryUseCase,
    val clearHistory: ClearHistoryUseCase

)