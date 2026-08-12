package com.divergentapp.qrtoolkit.features.history.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.arrow_back
import com.divergentapp.qrtoolkit.core.ui.icons.delete
import com.divergentapp.qrtoolkit.core.ui.icons.favorite
import com.divergentapp.qrtoolkit.core.ui.icons.favorite_filled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(
    showingFavorites: Boolean,
    onFavoriteFilterClick: () -> Unit,
    onDeleteAllClick: () -> Unit
) {

    CenterAlignedTopAppBar(

        windowInsets = WindowInsets(0,0,0,0),

        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.background
        ),

        title = {
            Text("History",
                color = MaterialTheme.colorScheme.tertiaryFixed,
                fontWeight = FontWeight.Bold)
        },

        navigationIcon = {
            IconButton(
                onClick = onFavoriteFilterClick
            ) {
                Icon(
                    imageVector =
                        if (showingFavorites)
                            favorite_filled
                        else
                            favorite,
                    contentDescription = "Favorites",
                    tint = MaterialTheme.colorScheme.tertiaryFixed
                )
            }

        },

        actions = {

            IconButton(
                onClick = onDeleteAllClick
            ) {
                Icon(
                    imageVector = delete,
                    contentDescription = "Delete history",
                    tint = MaterialTheme.colorScheme.tertiaryFixed
                )
            }

        },

    )

}