package com.divergentapp.qrtoolkit.features.scanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.add_photo_alternate
import com.divergentapp.qrtoolkit.core.ui.icons.flash_off
import com.divergentapp.qrtoolkit.core.ui.icons.flash_on

@Composable
fun ScannerTopBar(
    flashEnabled: Boolean,
    onGalleryClick: () -> Unit,
    onFlashClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 32.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CircleButton(
            onClick = onGalleryClick
        ) {
            Icon(
                imageVector = add_photo_alternate,
                contentDescription = "Gallery",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        CircleButton(
            onClick = onFlashClick
        ) {
            Icon(
                imageVector = if (flashEnabled)
                    flash_on
                else
                    flash_off,
                contentDescription = "Flash",
                tint = MaterialTheme.colorScheme.onPrimary

            )
        }


    }

}

@Composable
private fun CircleButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.clip(CircleShape),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(12.dp)
        ) {
            content()
        }

    }

}