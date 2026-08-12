package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.domain.model.QRContent

@Composable
fun GenerateEmailForm(
    content: QRContent.Email,
    onContentChanged: (QRContent.Email) -> Unit
) {

    OutlinedTextField(
        value = content.address,
        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
        onValueChange = {
            onContentChanged(
                content.copy(
                    address = it
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Address")
        },
        placeholder = {
            Text("john.doe@example.com")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )

    )

    OutlinedTextField(
        value = content.subject ?: "",
        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
        onValueChange = {
            onContentChanged(
                content.copy(
                    subject = it
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Subject")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )

    OutlinedTextField(
        value = content.body ?: "",
        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
        onValueChange = {
            onContentChanged(
                content.copy(
                    body = it
                )
            )
        },
        modifier = Modifier.fillMaxWidth()
            .height(140.dp),
        label = {
            Text("Content")
        },
        minLines = 5,
        maxLines = 10,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )

}