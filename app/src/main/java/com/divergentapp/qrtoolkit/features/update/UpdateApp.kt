package com.divergentapp.qrtoolkit.features.update

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.MainActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.tasks.await

@Composable
fun UpdateApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val appUpdateManager = remember(context) { AppUpdateManagerFactory.create(context) }

    // Assuming MainActivity.updated is a StateFlow<Boolean>
    val updated by MainActivity.updated.collectAsState()
    var showPopup by rememberSaveable { mutableStateOf(false) }

    // 1. Create the modern activity result launcher required for Jetpack Compose
    val updateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != android.app.Activity.RESULT_OK) {
            // Optional: Handle case where user declined the initial Google Play prompt
        }
    }

    // Listens for download progress updates while the app is active
    DisposableEffect(appUpdateManager) {
        val listener = InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showPopup = true
            }
        }
        appUpdateManager.registerListener(listener)
        onDispose {
            appUpdateManager.unregisterListener(listener)
        }
    }

    // 2. Removed the need for 'activity' / 'LocalActivity' entirely
    LaunchedEffect(appUpdateManager) {
        runCatching {
            appUpdateManager.appUpdateInfo.await()
        }.onSuccess { appUpdateInfo ->
            when {
                // If update already finished downloading before app launched
                appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED -> {
                    showPopup = true
                }

                // If update is available OR a background update was interrupted and is still downloading
                (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                        appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) &&
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        updateLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
                    )
                }
            }
        }
    }

    if (showPopup || updated) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            UpdatePopup(
                // 4. Fixed scaleDp() to standard Compose density units
                modifier = modifier.padding(bottom = 10.dp),
                onRestartPress = {
                    appUpdateManager.completeUpdate()
                },
            )
        }
    }
}