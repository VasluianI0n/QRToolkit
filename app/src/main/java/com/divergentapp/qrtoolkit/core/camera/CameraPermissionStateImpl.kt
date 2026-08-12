package com.divergentapp.qrtoolkit.core.camera

import com.divergentapp.qrtoolkit.core.permission.PermissionState

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.divergentapp.qrtoolkit.core.datastore.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

internal class CameraPermissionStateImpl(
    private val activity: Activity,
    private val launcher: ManagedActivityResultLauncher<String, Boolean>,
    private val dataStore: DataStoreManager,
    private val scope: CoroutineScope
) : CameraPermissionState {
    override var permission: PermissionState by mutableStateOf(PermissionState.Unknown)
        private set

    override fun refresh() {
        scope.launch {
            val requested = dataStore
                .cameraPermissionRequested
                .first()

            permission = when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    PermissionState.Granted
                }

                !requested -> {
                    PermissionState.Denied
                }

                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.CAMERA
                ) -> {
                    PermissionState.Denied
                }

                else -> {
                    PermissionState.PermanentlyDenied
                }
            }
        }

    }

    override fun requestPermission() {
        launcher.launch(
            Manifest.permission.CAMERA
        )
    }

}

@Composable
fun rememberCameraPermissionState(): CameraPermissionState {
    val activity = LocalActivity.current
    lateinit var state: CameraPermissionStateImpl
    val dataStoreManager : DataStoreManager = koinInject()
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        scope.launch {
            dataStoreManager.setCameraPermissionRequested(true)
        }

        state.refresh()
    }

    state = remember {
        CameraPermissionStateImpl(
            activity = activity!!,
            launcher = launcher,
            dataStore  = dataStoreManager,
            scope = scope
        )
    }

    LaunchedEffect(Unit) {
        state.refresh()
    }

    return state
}