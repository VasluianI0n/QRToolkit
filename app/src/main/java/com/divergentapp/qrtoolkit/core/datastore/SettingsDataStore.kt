package com.divergentapp.qrtoolkit.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.divergentapp.qrtoolkit.domain.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(
    private val context: Context
) {

    private object Keys {

        val DarkTheme = booleanPreferencesKey("dark_theme")

        val SystemTheme = booleanPreferencesKey("system_theme")

        val Vibrate = booleanPreferencesKey("vibrate")

        val Beep = booleanPreferencesKey("beep")

        val AutoOpenLinks = booleanPreferencesKey("auto_open_links")

        val SaveGeneratedHistory = booleanPreferencesKey("save_generated_history")

        val SaveScannedHistory = booleanPreferencesKey("save_scanned_history")

    }

    val settings: Flow<Settings> =
        context.dataStore.data.map { preferences ->

            Settings(
                darkTheme = preferences[Keys.DarkTheme] ?: false,
                useSystemTheme = preferences[Keys.SystemTheme] ?: true,
                vibrateOnScan = preferences[Keys.Vibrate] ?: true,
                beepOnScan = preferences[Keys.Beep] ?: true,
                autoOpenLinks = preferences[Keys.AutoOpenLinks] ?: false,
                saveGeneratedHistory = preferences[Keys.SaveGeneratedHistory] ?: false,
                saveScannedHistory = preferences[Keys.SaveScannedHistory] ?: true
            )

        }

    suspend fun setDarkTheme(enabled: Boolean) =
        updatePreference(Keys.DarkTheme, enabled)

    suspend fun setUseSystemTheme(enabled: Boolean) =
        updatePreference(Keys.SystemTheme, enabled)

    suspend fun setVibration(enabled: Boolean) =
        updatePreference(Keys.Vibrate, enabled)

    suspend fun setBeep(enabled: Boolean) =
        updatePreference(Keys.Beep, enabled)

    suspend fun setAutoOpenLinks(enabled: Boolean) =
        updatePreference(Keys.AutoOpenLinks, enabled)

    suspend fun setSaveGeneratedHistory(enabled: Boolean) =
        updatePreference(Keys.SaveGeneratedHistory, enabled)

    suspend fun setSaveScannedHistory(enabled: Boolean) =
        updatePreference(Keys.SaveScannedHistory, enabled)

    private suspend fun updatePreference(
        key: Preferences.Key<Boolean>,
        value: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

}