package com.divergentapp.qrtoolkit.features.history.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.domain.model.QRHistory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryListItem(
    item: QRHistory,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onFavoriteClick: () -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
            }
            true
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            DeleteBackground(
                dismissState = dismissState
            )
        }

    ) {

        HistoryItem(
            item = item,
            onClick = onClick,
            onFavoriteClick = onFavoriteClick
        )

    }

}