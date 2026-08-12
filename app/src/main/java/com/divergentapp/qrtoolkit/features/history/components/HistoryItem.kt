package com.divergentapp.qrtoolkit.features.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.extensions.displayType
import com.divergentapp.qrtoolkit.core.ui.extensions.formatHistoryDate
import com.divergentapp.qrtoolkit.core.ui.extensions.icon
import com.divergentapp.qrtoolkit.core.ui.extensions.title
import com.divergentapp.qrtoolkit.core.ui.icons.favorite
import com.divergentapp.qrtoolkit.core.ui.icons.favorite_filled
import com.divergentapp.qrtoolkit.core.ui.icons.star
import com.divergentapp.qrtoolkit.core.ui.icons.star_border
import com.divergentapp.qrtoolkit.domain.model.QRHistory

@Composable
fun HistoryItem(
    item: QRHistory,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = item.content.icon(),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.tertiaryFixed
            )

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = item.content.title(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiaryFixed
                )

                Spacer(Modifier.height(1.dp))

                Text(
                    text = item.rawValue,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiaryFixed
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = item.createdAt.formatHistoryDate(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )

            }

            IconButton(
                onClick = onFavoriteClick
            ) {

                Icon(
                    imageVector =
                        if (item.isFavorite)
                            favorite_filled
                        else
                            favorite,
                    contentDescription = null,
                    tint =
                        if (item.isFavorite)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.tertiaryFixed
                )

            }

        }

    }

}