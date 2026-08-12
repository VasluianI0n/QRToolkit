package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenerateTypeSelector(
    selected: GenerateType,
    onSelected: (GenerateType) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsIndexed(GenerateType.entries) { _, type ->

            FilterChip(

                selected = selected == type,

                onClick = {
                    onSelected(type)
                },

                label = {
                    Text(type.title)
                }

            )

        }

    }

}