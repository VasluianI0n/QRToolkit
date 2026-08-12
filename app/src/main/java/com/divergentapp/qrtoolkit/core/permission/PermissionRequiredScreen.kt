package com.divergentapp.qrtoolkit.core.permission

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.camera

@Composable
fun PermissionRequiredScreen(
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = camera,
            contentDescription = null
        )

        Text(
            text = "Camera permission required",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Please allow camera access to scan QR codes.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onRequestPermission
        ) {
            Text("Grant Permission")
        }

    }

}