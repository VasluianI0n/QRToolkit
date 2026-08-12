package com.divergentapp.qrtoolkit.core.camera

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.divergentapp.qrtoolkit.core.permission.PermissionState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

@Composable
fun CameraPermission(

    onPermissionChanged: (PermissionState) -> Unit

) {

    val context = LocalContext.current

    val activity = context as Activity

    var state by remember {

        mutableStateOf<PermissionState>(
            PermissionState.Unknown
        )

    }

    val launcher = rememberLauncherForActivityResult(

        ActivityResultContracts.RequestPermission()

    ) { granted ->

        state = when {

            granted -> PermissionState.Granted

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            ) -> PermissionState.Denied

            else -> PermissionState.PermanentlyDenied

        }

        onPermissionChanged(state)

    }

    LaunchedEffect(Unit) {

        state = when {

            ContextCompat.checkSelfPermission(

                context,

                Manifest.permission.CAMERA

            ) == PackageManager.PERMISSION_GRANTED -> {

                PermissionState.Granted

            }

            else -> {

                launcher.launch(
                    Manifest.permission.CAMERA
                )

                PermissionState.Unknown

            }

        }

        if (state != PermissionState.Unknown) {

            onPermissionChanged(state)

        }

    }

}