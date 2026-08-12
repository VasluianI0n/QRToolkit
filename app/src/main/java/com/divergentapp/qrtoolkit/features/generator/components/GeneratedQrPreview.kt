package com.divergentapp.qrtoolkit.features.generator.components

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun GeneratedQrPreview(
    bitmap: Bitmap?,
    modifier: Modifier = Modifier
) {
    if (bitmap == null) return

        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.background)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {

                Image(

                    bitmap = bitmap.asImageBitmap(),

                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)

                )

            }

        }


}