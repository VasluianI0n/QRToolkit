package com.divergentapp.qrtoolkit.features.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.divergentapp.qrtoolkit.core.ui.icons.keyboard_arrow_right

@Composable
fun SettingItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {

    ListItem(

        modifier = Modifier.clickable(onClick = onClick),


        leadingContent = {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.tertiaryFixed,
                contentDescription = null
            )
        },

        colors = ListItemDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),

        headlineContent = {
            Text(
                title,
                color = MaterialTheme.colorScheme.tertiaryFixed
            )
        },

        trailingContent = {
            Icon(
                keyboard_arrow_right,
                null,
                tint = MaterialTheme.colorScheme.tertiaryFixed
            )
        }

    )

}