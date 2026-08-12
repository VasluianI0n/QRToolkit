package com.divergentapp.qrtoolkit.features.settings.intent

import com.divergentapp.qrtoolkit.core.mvi.UiIntent

sealed interface SettingsIntent: UiIntent{

    data class ToggleDarkTheme(val enabled: Boolean) : SettingsIntent
    data object ToggleSystemTheme : SettingsIntent
    data object ToggleVibration : SettingsIntent
    data object ToggleBeep : SettingsIntent
    data object ToggleAutoOpenLinks : SettingsIntent
    data object ToggleGeneratedHistory : SettingsIntent
    data object ToggleScannedHistory : SettingsIntent
    data object ClearHistory : SettingsIntent
    data object RateApp : SettingsIntent
    data object OpenPlayStore: SettingsIntent
    data object ShareApp : SettingsIntent
    data object ContactSupport : SettingsIntent
    data object PrivacyPolicy : SettingsIntent

}