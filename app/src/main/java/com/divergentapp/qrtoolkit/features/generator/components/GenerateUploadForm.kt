package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.upload

@Composable
fun GenerateUploadForm(
    fileName: String,
    isUploading: Boolean,
    uploadProgress: Int,
    chooseText: String,
    chooseAnotherText: String,
    fileIcon: ImageVector,
    onPickFile: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.Center
    ) {

        if (isUploading) {

            val animatedProgress by animateFloatAsState(
                targetValue = uploadProgress / 100f,
                label = "Upload"
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator(
                    progress = { animatedProgress }
                )

                Spacer(Modifier.height(16.dp))

                Text("$uploadProgress%")
            }

        } else {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable(onClick = onPickFile),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    ),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    )
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = upload,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = if (fileName.isBlank())
                                chooseText
                            else
                                chooseAnotherText,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiaryFixed
                        )
                    }
                }

                if (fileName.isNotBlank()) {

                    Spacer(Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = fileIcon,
                            contentDescription = null
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(fileName)
                    }
                }
            }
        }
    }
}