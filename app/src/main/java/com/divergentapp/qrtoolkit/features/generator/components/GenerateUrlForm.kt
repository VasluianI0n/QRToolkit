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
fun GenerateUrlForm(
    content: QRContent.Url,
    onContentChanged: (QRContent.Url) -> Unit
) {

    OutlinedTextField(

        value = content.url,

        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),

        onValueChange = {

            onContentChanged(
                content.copy(
                    url = it
                )
            )

        },

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("Website")
        },

        placeholder = {
            Text("https://example.com")
        },

        singleLine = true,

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri
        )

    )

}