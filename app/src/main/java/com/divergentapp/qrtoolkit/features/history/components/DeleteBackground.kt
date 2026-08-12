package com.divergentapp.qrtoolkit.features.history.components

import android.view.RoundedCorner
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    dismissState: SwipeToDismissBoxState
) {

    val color by animateColorAsState(
        targetValue =
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart)
                MaterialTheme.colorScheme.error
            else
                Color.Transparent,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(color)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd

    ) {
        Icon(
            imageVector = delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onError
        )
    }
}