package com.divergentapp.qrtoolkit.features.history.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.features.history.state.HistorySection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    sections: List<HistorySection>,
    modifier: Modifier = Modifier,
    onItemClick: (QRHistory) -> Unit,
    onDelete: (QRHistory) -> Unit,
    onFavoriteClick: (QRHistory) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 96.dp)
    ) {

        sections.forEach { section ->

            stickyHeader {
                HistorySectionHeader(
                    title = section.title
                )
            }

            items(
                items = section.items,
                key = { it.id }
            ) { item ->
                HistoryListItem(
                    item = item,
                    onClick = {
                        onItemClick(item)
                    },
                    onDelete = {
                        onDelete(item)
                    },
                    onFavoriteClick = {
                        onFavoriteClick(item)
                    }
                )
            }

        }

    }

}