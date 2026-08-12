package com.divergentapp.qrtoolkit.features.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SettingGroup(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp
        ) {

            Column(
                content = content
            )

        }

    }

}