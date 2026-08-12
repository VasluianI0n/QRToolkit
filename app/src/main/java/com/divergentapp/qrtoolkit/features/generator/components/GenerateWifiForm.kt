package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.domain.model.WifiEncryption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateWifiForm(
    content: QRContent.Wifi,
    onContentChanged: (QRContent) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = content.ssid,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            onValueChange = {
                onContentChanged(
                    content.copy(ssid = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Wi-Fi name (SSID)")
            },
            singleLine = true
        )

        OutlinedTextField(
            value = content.password,
            colors = OutlinedTextFieldDefaults.colors()
                .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                onContentChanged(
                    content.copy(password = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Password")
            },
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = content.encryption,
                colors = OutlinedTextFieldDefaults.colors()
                    .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                        unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                label = {
                    Text("Encryption")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                WifiEncryption.entries.forEach { encryption ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                when (encryption) {
                                    WifiEncryption.WPA -> "WPA"
                                    WifiEncryption.WEP -> "WEP"
                                    WifiEncryption.NONE -> "None"
                                },
                                color = MaterialTheme.colorScheme.tertiaryFixed
                            )
                        },
                        onClick = {

                            expanded = false

                            onContentChanged(
                                content.copy(
                                    encryption = encryption.name
                                )
                            )

                        }
                    )

                }

            }

        }

    }

}