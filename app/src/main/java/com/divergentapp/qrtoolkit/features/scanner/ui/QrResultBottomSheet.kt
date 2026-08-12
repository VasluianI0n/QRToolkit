package com.divergentapp.qrtoolkit.features.scanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.qr.ParsedQR
import com.divergentapp.qrtoolkit.core.ui.extensions.description
import com.divergentapp.qrtoolkit.core.ui.extensions.title
import com.divergentapp.qrtoolkit.core.ui.icons.content_copy
import com.divergentapp.qrtoolkit.core.ui.icons.favorite
import com.divergentapp.qrtoolkit.core.ui.icons.favorite_filled
import com.divergentapp.qrtoolkit.core.ui.icons.open_in_browser
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code_2
import com.divergentapp.qrtoolkit.core.ui.icons.share

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrResultBottomSheet(
    parsedQr: ParsedQR,
    onDismiss: () -> Unit,
    onOpen: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onFavorite: (() -> Unit)? = null,
    onPrimaryAction: (() -> Unit)? = null,
    primaryActionText: String = "Scan Again"
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)

        ) {
            ResultHeader(parsedQr)
            Spacer(Modifier.height(24.dp))
            ResultContent(parsedQr)
            Spacer(Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(Modifier.height(24.dp))
            ResultActions(
                onOpen = onOpen,
                onCopy = onCopy,
                onShare = onShare,
                onFavorite = onFavorite
            )
            Spacer(Modifier.height(24.dp))
            if (onPrimaryAction != null) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onPrimaryAction
                ) {
                    Text(primaryActionText)
                }
                Spacer(Modifier.height(24.dp))
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ResultHeader(
    parsedQr: ParsedQR
) {
    Column {
        Icon(
            imageVector = qr_code_2,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.tertiaryFixed
        )

        Spacer(Modifier.height(12.dp))
        Text(
            text = parsedQr.content.title(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.tertiaryFixed
        )
    }
}

@Composable
private fun ResultContent(
    parsedQr: ParsedQR
) {
    Column {
        Text(
            text = parsedQr.content.description(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiaryFixed
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Raw value",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiaryFixed
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = parsedQr.rawValue,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiaryFixed
        )
    }
}

@Composable
private fun ResultActions(
    onOpen: () -> Unit,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onFavorite: (() -> Unit)?
) {
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomActionButton (
            icon = open_in_browser,
            text = "Open",
            onClick = onOpen
        )

        BottomActionButton(
            icon = content_copy,
            text = "Copy",
            onClick = onCopy
        )

        BottomActionButton(
            icon = share,
            text = "Share",
            onClick = onShare
        )

        if (onFavorite != null) {
            BottomActionButton(
                icon = if (isFavorite) favorite_filled else favorite,
                text = "Favorite",
                onClick = {
                    isFavorite = !isFavorite
                    onFavorite()
                }
            )
        }
    }
}