package com.divergentapp.qrtoolkit.features.history.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.close
import com.divergentapp.qrtoolkit.core.ui.icons.search

@Composable
fun HistorySearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        value = value,

        colors = OutlinedTextFieldDefaults.colors()
            .copy(focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed),

        onValueChange = onValueChange,

        singleLine = true,

        placeholder = {
            Text("Search history")
        },

        leadingIcon = {

            Icon(
                imageVector = search,
                contentDescription = null
            )

        },

        trailingIcon = {

            if (value.isNotEmpty()) {

                IconButton(
                    onClick = {
                        onValueChange("")
                    }
                ) {

                    Icon(
                        imageVector = close,
                        contentDescription = "Clear"
                    )

                }

            }

        }

    )

}