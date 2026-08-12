package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.divergentapp.qrtoolkit.domain.model.QRContent

@Composable
fun GenerateLocationForm(
    content: QRContent.Location,
    onContentChanged: (QRContent.Location) -> Unit
) {

    OutlinedTextField(
        value = content.latitude,
        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
        onValueChange = {
            onContentChanged(
                content.copy(
                    latitude = it
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Latitude")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )

    )

    OutlinedTextField(
        value = content.longitude,
        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
        onValueChange = {
            onContentChanged(
                content.copy(
                    longitude = it
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Longitude")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )

}