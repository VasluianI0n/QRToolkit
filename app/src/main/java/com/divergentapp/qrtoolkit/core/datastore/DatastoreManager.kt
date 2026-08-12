package com.divergentapp.qrtoolkit.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    context: Context
) {

    private val dataStore = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("qr_toolkit_preferences")
        }
    )

    private object Keys {

        val CameraPermissionRequested =
            booleanPreferencesKey("camera_permission_requested")

    }

    val cameraPermissionRequested: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[Keys.CameraPermissionRequested] ?: false
        }

    suspend fun setCameraPermissionRequested(
        requested: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[Keys.CameraPermissionRequested] = requested
        }
    }
}