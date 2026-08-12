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
fun GeneratePhoneForm(
    content: QRContent.Phone,
    onContentChanged: (QRContent.Phone) -> Unit
) {

    OutlinedTextField(

        value = content.number,

        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),

        onValueChange = {

            onContentChanged(
                content.copy(
                    number = it
                )
            )

        },

        modifier = Modifier.fillMaxWidth(),

        label = {
            Text("Phone Number")
        },

        placeholder = {
            Text("+1 (213) XXX-XXXX")
        },

        singleLine = true,

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        )

    )

}