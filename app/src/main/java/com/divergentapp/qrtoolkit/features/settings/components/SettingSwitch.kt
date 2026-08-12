package com.divergentapp.qrtoolkit.features.settings.components

import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SettingSwitch(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) {
                    MaterialTheme.colorScheme.tertiaryFixed
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        },
        headlineContent = {
            Text(
                text = title,
                color = if (enabled) {
                    MaterialTheme.colorScheme.tertiaryFixed
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    disabledCheckedThumbColor = MaterialTheme.colorScheme.outline,
                    disabledCheckedTrackColor = MaterialTheme.colorScheme.outlineVariant,
                    disabledUncheckedThumbColor = MaterialTheme.colorScheme.outline,
                    disabledUncheckedTrackColor = MaterialTheme.colorScheme.outlineVariant,
                    disabledCheckedBorderColor = MaterialTheme.colorScheme.outline,
                    disabledUncheckedBorderColor = MaterialTheme.colorScheme.outline,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    )
}