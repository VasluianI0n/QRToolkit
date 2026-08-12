package com.divergentapp.qrtoolkit.features.settings.state

import com.divergentapp.qrtoolkit.core.mvi.BaseScreenState

data class SettingsState(

    val darkTheme: Boolean = false,
    val useSystemState: Boolean = true,

    val vibrateOnScan: Boolean = true,
    val beepOnScan: Boolean = true,
    val autoOpenLinks: Boolean = false,

    val highQualityQr: Boolean = true,
    val includeMargin: Boolean = true,

    val saveGeneratedHistory: Boolean = false,
    val saveScannedHistory: Boolean = true,

    val appVersion: String = "1.0.0"

): BaseScreenState(false, null)