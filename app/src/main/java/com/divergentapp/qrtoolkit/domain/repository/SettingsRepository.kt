package com.divergentapp.qrtoolkit.domain.repository

import com.divergentapp.qrtoolkit.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settings: Flow<Settings>

    suspend fun setDarkTheme(enabled: Boolean)

    suspend fun setUseSystemTheme(enabled: Boolean)

    suspend fun setVibration(enabled: Boolean)

    suspend fun setBeep(enabled: Boolean)

    suspend fun setAutoOpenLinks(enabled: Boolean)

    suspend fun setSaveGeneratedHistory(enabled: Boolean)

    suspend fun setSaveScannedHistory(enabled: Boolean)

}