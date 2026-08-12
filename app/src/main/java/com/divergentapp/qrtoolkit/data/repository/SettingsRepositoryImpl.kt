package com.divergentapp.qrtoolkit.data.repository
import com.divergentapp.qrtoolkit.core.datastore.SettingsDataStore
import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override val settings: Flow<Settings> =
        dataStore.settings

    override suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.setDarkTheme(enabled)
    }

    override suspend fun setUseSystemTheme(enabled: Boolean) {
        dataStore.setUseSystemTheme(enabled)
    }

    override suspend fun setVibration(enabled: Boolean) {
        dataStore.setVibration(enabled)
    }

    override suspend fun setBeep(enabled: Boolean) {
        dataStore.setBeep(enabled)
    }

    override suspend fun setAutoOpenLinks(enabled: Boolean) {
        dataStore.setAutoOpenLinks(enabled)
    }

    override suspend fun setSaveGeneratedHistory(enabled: Boolean) {
        dataStore.setSaveGeneratedHistory(enabled)
    }

    override suspend fun setSaveScannedHistory(enabled: Boolean) {
        dataStore.setSaveScannedHistory(enabled)
    }

}