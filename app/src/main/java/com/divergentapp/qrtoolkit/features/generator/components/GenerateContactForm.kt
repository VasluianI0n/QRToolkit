package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
fun GenerateContactForm(
    content: QRContent.Contact,
    onContentChanged: (QRContent) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = content.firstName,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(firstName = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("First name")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = content.lastName,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(lastName = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Last name")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = content.company,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(company = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Company")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = content.phone,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(phone = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Phone")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            singleLine = true
        )

        OutlinedTextField(
            value = content.email,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(email = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true
        )

    }

}