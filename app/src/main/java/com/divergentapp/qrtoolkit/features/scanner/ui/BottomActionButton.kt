package com.divergentapp.qrtoolkit.features.scanner.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.onPrimary,
            onClick = onClick,
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
        ) {

            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.tertiaryFixed
            )

        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.tertiaryFixed,
            style = MaterialTheme.typography.bodyMedium
        )

    }

}