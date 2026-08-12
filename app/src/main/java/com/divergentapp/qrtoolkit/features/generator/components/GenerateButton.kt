package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code

@Composable
fun GenerateButton(
    enabled: Boolean,
    onClick: () -> Unit
) {

    Button(

        enabled = enabled,

        onClick = onClick,

        modifier = Modifier.fillMaxWidth()

    ) {

        Icon(

            imageVector = qr_code,

            contentDescription = null

        )

        Spacer(
            Modifier.width(8.dp)
        )

        Text("Generate QR")

    }

}