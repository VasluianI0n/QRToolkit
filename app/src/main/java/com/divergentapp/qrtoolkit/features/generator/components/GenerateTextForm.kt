package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.divergentapp.qrtoolkit.domain.model.QRContent

@Composable
fun GenerateTextForm(
    content: QRContent.Text,
    onContentChanged: (QRContent.Text) -> Unit
) {

    OutlinedTextField(

        value = content.text,

        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),

        onValueChange = {

            onContentChanged(
                content.copy(
                    text = it
                )
            )

        },

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("Text")
        },

        minLines = 5

    )

}