package com.divergentapp.qrtoolkit.features.generator.screen

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.divergentapp.qrtoolkit.core.ads.InterstitialAdManager
import com.divergentapp.qrtoolkit.core.common.FeedbackManager
import com.divergentapp.qrtoolkit.core.common.VibrationManager
import com.divergentapp.qrtoolkit.core.util.GenerateValidator.canGenerate
import com.divergentapp.qrtoolkit.features.generator.components.GenerateButton
import com.divergentapp.qrtoolkit.features.generator.components.GenerateContentForm
import com.divergentapp.qrtoolkit.features.generator.components.GenerateType
import com.divergentapp.qrtoolkit.features.generator.components.GenerateTypeSelector
import com.divergentapp.qrtoolkit.features.generator.components.GeneratedQrPreview
import com.divergentapp.qrtoolkit.features.generator.model.GenerateEffect
import com.divergentapp.qrtoolkit.features.generator.model.GenerateIntent
import com.divergentapp.qrtoolkit.features.generator.viewmodel.GenerateViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateScreen(
    viewModel: GenerateViewModel = koinViewModel()
) {

    val activity = LocalActivity.current
    val adManager : InterstitialAdManager = koinInject()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    val feedbackManager: FeedbackManager = koinInject()

    LaunchedEffect(state.bitmap) {
        if (state.bitmap != null) feedbackManager.play(settings)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GenerateEffect.ShowMessage -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                GenerateEffect.ShowInterstitial -> {
                    if (adManager.recordAction()) {
                        activity?.let {
                            adManager.showIfAvailable(it)
                        }
                    }
                }
            }
        }
    }

    val launcherPdf =
        rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            val tempFile = uriToTempFile(context, uri)
            viewModel.dispatch(GenerateIntent.FileSelected(tempFile, "application/pdf"))
        }

    val launcherImage = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        val tempFile = uriToTempFile(context, uri)
        viewModel.dispatch(GenerateIntent.FileSelected(tempFile, "image/*"))
    }

    val launcherVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        val tempFile = uriToTempFile(context, uri)
        viewModel.dispatch(GenerateIntent.FileSelected(tempFile, "video/*"))
    }

    val launcherAudio = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        val tempFile = uriToTempFile(context, uri)
        viewModel.dispatch(GenerateIntent.FileSelected(tempFile, "audio/*"))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .imePadding(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {
            GenerateTypeSelector(
                selected = state.selectedType,
                onSelected = {
                    viewModel.dispatch(
                        GenerateIntent.SelectType(it)
                    )
                }
            )
        }

        item {
            GenerateContentForm(
                content = state.content,
                isUploading = state.isUploading,
                onContentChanged = {
                    viewModel.dispatch(
                        GenerateIntent.UpdateContent(it)
                    )
                },
                type = state.selectedType,
                uploadProgress = state.uploadProgress,
                onFilePick = {
                    when (state.selectedType) {
                        GenerateType.PDF ->
                            launcherPdf.launch(arrayOf("application/pdf"))

                        GenerateType.IMAGE ->
                            launcherImage.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )

                        GenerateType.VIDEO ->
                            launcherVideo.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.VideoOnly
                                )
                            )

                        GenerateType.AUDIO ->
                            launcherAudio.launch(arrayOf("audio/*"))

                        else -> Unit
                    }
                }
            )
        }

        if(state.selectedType != GenerateType.IMAGE &&
            state.selectedType != GenerateType.VIDEO &&
            state.selectedType != GenerateType.AUDIO &&
            state.selectedType != GenerateType.PDF){
            item {
                GenerateButton(
                    enabled = !state.isUploading && canGenerate(state.content),
                    onClick = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        viewModel.dispatch(GenerateIntent.Generate)
                    }
                )
            }
        }

    }

    state.bitmap?.let { bitmap ->

        ModalBottomSheet(
            onDismissRequest = {
                viewModel.dispatch(
                    GenerateIntent.DismissPreview
                )
            }
        ) {

            Column {
                GeneratedQrPreview(
                    bitmap = bitmap
                )

                Spacer(Modifier.height(35.dp))
            }


        }
    }

}

fun uriToTempFile(
    context: Context,
    uri: Uri
): File {

    val mimeType = context.contentResolver.getType(uri)
        ?: "application/octet-stream"

    val extension = MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(mimeType)
        ?: "tmp"

    val file = File(
        context.cacheDir,
        "upload_${System.currentTimeMillis()}.$extension"
    )

    context.contentResolver.openInputStream(uri)?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file
}
