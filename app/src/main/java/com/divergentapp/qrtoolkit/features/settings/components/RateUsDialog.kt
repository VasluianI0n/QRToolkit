package com.divergentapp.qrtoolkit.features.settings.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.divergentapp.qrtoolkit.core.ui.icons.star

@Composable
fun RateUsDialog(
    onDismiss: () -> Unit,
    onRatingSelected: (Int) -> Unit
) {
    var rating by rememberSaveable {
        mutableIntStateOf(0)
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(28.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = .12f)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )

                }

                Spacer(Modifier.height(24.dp))

                Text(
                    "Enjoying QR Toolkit?",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.surface
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Your feedback helps us improve the app.\nHow would you rate your experience?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(28.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    repeat(5) { index ->

                        val selected = index < rating

                        val scale by animateFloatAsState(
                            if (selected) 1.25f else 1f,
                            label = ""
                        )

                        Icon(
                            imageVector = star,
                            contentDescription = null,
                            tint =
                                if (selected)
                                    Color(0xFFFFC107)
                                else
                                    MaterialTheme.colorScheme.outline.copy(alpha = .5f),
                            modifier = Modifier
                                .size(42.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .clickable(
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    rating = index + 1
                                }
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                FilledTonalButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = rating != 0,
                    onClick = {
                        onRatingSelected(rating)
                    }
                ) {

                    Text("Continue")

                }

                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Maybe later")
                }
            }
        }
    }
}