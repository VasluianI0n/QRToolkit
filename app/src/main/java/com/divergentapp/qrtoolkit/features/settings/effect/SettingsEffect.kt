package com.divergentapp.qrtoolkit.features.settings.effect

import com.divergentapp.qrtoolkit.core.mvi.UiEffect

sealed interface SettingsEffect : UiEffect {
    data object OpenRateUs : SettingsEffect
    data object OpenPlayStore : SettingsEffect
    data object ShareApp : SettingsEffect
    data object OpenPrivacyPolicy : SettingsEffect
    data object ContactSupport : SettingsEffect
}