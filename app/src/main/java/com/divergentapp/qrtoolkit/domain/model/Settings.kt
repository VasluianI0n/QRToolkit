package com.divergentapp.qrtoolkit.domain.model

data class Settings(

    val darkTheme: Boolean = false,
    val useSystemTheme: Boolean = true,

    val vibrateOnScan: Boolean = true,
    val beepOnScan: Boolean = true,
    val autoOpenLinks: Boolean = false,

    val saveGeneratedHistory: Boolean = false,
    val saveScannedHistory: Boolean = true

)