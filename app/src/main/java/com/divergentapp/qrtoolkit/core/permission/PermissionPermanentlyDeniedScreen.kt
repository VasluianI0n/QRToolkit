package com.divergentapp.qrtoolkit.core.permission

import com.divergentapp.qrtoolkit.core.ui.icons.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PermissionPermanentlyDeniedScreen() {

    val context = LocalContext.current

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Icon(
            imageVector = settings,
            contentDescription = null
        )

        Text(

            text = "Camera permission denied",

            style = MaterialTheme.typography.headlineSmall

        )

        Text(

            text = "Enable the permission from App Settings.",

            modifier = Modifier.padding(top = 12.dp)

        )

        Button(

            modifier = Modifier.padding(top = 24.dp),

            onClick = {

                val intent = Intent(

                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,

                    Uri.fromParts(
                        "package",
                        context.packageName,
                        null
                    )

                )

                context.startActivity(intent)

            }

        ) {

            Text("Open Settings")

        }

    }

}