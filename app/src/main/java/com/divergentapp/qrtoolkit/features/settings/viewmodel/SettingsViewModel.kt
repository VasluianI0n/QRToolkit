package com.divergentapp.qrtoolkit.features.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.divergentapp.qrtoolkit.BuildConfig
import com.divergentapp.qrtoolkit.core.mvi.BaseViewModel
import com.divergentapp.qrtoolkit.domain.usecase.SettingsUseCases
import com.divergentapp.qrtoolkit.features.settings.effect.SettingsEffect
import com.divergentapp.qrtoolkit.features.settings.intent.SettingsIntent
import com.divergentapp.qrtoolkit.features.settings.state.SettingsState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val useCases: SettingsUseCases,
) : BaseViewModel<
    SettingsIntent,
    SettingsState,
    SettingsEffect>
    (SettingsState()) {

    private val _state = useCases.getSettings()
        .map { settings ->
            SettingsState(
                darkTheme = settings.darkTheme,
                useSystemState = settings.useSystemTheme,
                vibrateOnScan = settings.vibrateOnScan,
                beepOnScan = settings.beepOnScan,
                autoOpenLinks = settings.autoOpenLinks,
                saveGeneratedHistory = settings.saveGeneratedHistory,
                saveScannedHistory = settings.saveScannedHistory,
                appVersion = BuildConfig.VERSION_NAME
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsState(
                appVersion = BuildConfig.VERSION_NAME
            )
        )

    val settingsState: StateFlow<SettingsState> = _state

    override fun onIntent(intent: SettingsIntent) {
        viewModelScope.launch {

            val current = _state.value

            when (intent) {

                is SettingsIntent.ToggleDarkTheme ->
                    useCases.setDarkTheme(intent.enabled)

                SettingsIntent.ToggleSystemTheme ->
                    useCases.setUseSystemTheme(!current.useSystemState)

                SettingsIntent.ToggleVibration ->
                    useCases.setVibration(!current.vibrateOnScan)

                SettingsIntent.ToggleBeep ->
                    useCases.setBeep(!current.beepOnScan)

                SettingsIntent.ToggleAutoOpenLinks ->
                    useCases.setAutoOpenLinks(!current.autoOpenLinks)

                SettingsIntent.ToggleGeneratedHistory ->
                    useCases.setSaveGeneratedHistory(!current.saveGeneratedHistory)

                SettingsIntent.ToggleScannedHistory ->
                    useCases.setSaveScannedHistory(!current.saveScannedHistory)

                SettingsIntent.ClearHistory ->
                    useCases.clearHistory()

                SettingsIntent.RateApp -> {
                    sendEffect(SettingsEffect.OpenRateUs)
                }

                SettingsIntent.OpenPlayStore -> {
                    sendEffect(SettingsEffect.OpenPlayStore)
                }

                SettingsIntent.ShareApp -> {
                    sendEffect(SettingsEffect.ShareApp)
                }

                SettingsIntent.ContactSupport -> {
                    sendEffect(SettingsEffect.ContactSupport)
                }

                SettingsIntent.PrivacyPolicy -> {
                    sendEffect(SettingsEffect.OpenPrivacyPolicy)
                }

            }

        }
    }

}