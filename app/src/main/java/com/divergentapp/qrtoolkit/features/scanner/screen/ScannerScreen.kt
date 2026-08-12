package com.divergentapp.qrtoolkit.features.scanner.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.divergentapp.qrtoolkit.core.common.BeepManager
import com.divergentapp.qrtoolkit.core.camera.CameraController
import com.divergentapp.qrtoolkit.core.camera.CameraPreview
import com.divergentapp.qrtoolkit.core.camera.rememberCameraPermissionState
import com.divergentapp.qrtoolkit.core.common.FeedbackManager
import com.divergentapp.qrtoolkit.core.common.VibrationManager
import com.divergentapp.qrtoolkit.core.permission.PermissionPermanentlyDeniedScreen
import com.divergentapp.qrtoolkit.core.permission.PermissionRequiredScreen
import com.divergentapp.qrtoolkit.core.permission.PermissionState
import com.divergentapp.qrtoolkit.core.qr.GalleryQrScanner
import com.divergentapp.qrtoolkit.core.qr.QRAnalyzerFactory
import com.divergentapp.qrtoolkit.features.scanner.model.ScannerEffect
import com.divergentapp.qrtoolkit.features.scanner.ui.ScannerAnimatedLaser
import com.divergentapp.qrtoolkit.features.scanner.ui.ScannerBottomBar
import com.divergentapp.qrtoolkit.features.scanner.ui.ScannerOverlay
import com.divergentapp.qrtoolkit.features.scanner.ui.ScannerTopBar
import com.divergentapp.qrtoolkit.features.scanner.model.ScannerIntent
import com.divergentapp.qrtoolkit.features.scanner.ui.QrResultBottomSheet
import com.divergentapp.qrtoolkit.features.scanner.viewmodel.ScannerViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import com.divergentapp.qrtoolkit.core.ui.copyQr
import com.divergentapp.qrtoolkit.core.ui.openQrContent
import com.divergentapp.qrtoolkit.core.ui.shareQr
import com.divergentapp.qrtoolkit.domain.model.QRContent

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissionState = rememberCameraPermissionState()
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    val controller: CameraController = koinInject()
    val analyzerFactory: QRAnalyzerFactory = koinInject()
    val galleryQrScanner: GalleryQrScanner = koinInject()
    val feedbackManager: FeedbackManager = koinInject()


    LaunchedEffect(state.scannedQr) {
        val qr = state.scannedQr ?: return@LaunchedEffect

        feedbackManager.play(settings)

        if (
            settings.autoOpenLinks &&
            qr.content is QRContent.Url
        ) {
            viewModel.dispatch(ScannerIntent.OpenResult)
        }
    }

    LaunchedEffect(permissionState.permission) {
        if (permissionState.permission == PermissionState.Denied) {
            permissionState.requestPermission()
        }
    }

    val analyzer = remember(analyzerFactory) {
        analyzerFactory.create { parsed ->
            viewModel.dispatch(
                ScannerIntent.QRDetected(parsed)
            )
        }
    }

    val galleryPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            val parsed = galleryQrScanner.scan(uri)
            if (parsed != null) {
                viewModel.dispatch(
                    ScannerIntent.QRDetected(parsed)
                )
            } else {
                viewModel.dispatch(
                    ScannerIntent.GalleryScanFailed
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ScannerEffect.OpenGalleryPicker -> {
                    galleryPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
                is ScannerEffect.Open -> {
                    context.openQrContent(
                        effect.content
                    )
                }
                is ScannerEffect.Copy -> {
                    context.copyQr(
                        effect.rawValue
                    )
                }
                is ScannerEffect.Share -> {
                    context.shareQr(
                        effect.rawValue
                    )
                }
                is ScannerEffect.ShowMessage -> {
                    Toast
                        .makeText(
                            context,
                            effect.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (permissionState.permission) {
            PermissionState.Unknown -> {
                // Replace with LoadingScreen if desired
            }

            PermissionState.Granted -> {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    analyzer = analyzer,
                    controller = controller
                )

                if (state.isScanning) {
                    ScannerAnimatedLaser()
                }

                ScannerOverlay()

                ScannerTopBar(
                    flashEnabled = state.flashEnabled,
                    onGalleryClick = {
                        viewModel.dispatch(
                            ScannerIntent.OpenGallery
                        )
                    },
                    onFlashClick = {
                        viewModel.dispatch(
                            ScannerIntent.ToggleFlash
                        )
                    }
                )

                ScannerBottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                )

                state.scannedQr?.let { scannedQr ->
                    QrResultBottomSheet(
                        parsedQr = scannedQr,
                        onDismiss = {
                            viewModel.dispatch(
                                ScannerIntent.DismissResult
                            )
                        },
                        onOpen = {
                            viewModel.dispatch(
                                ScannerIntent.OpenResult
                            )
                        },
                        onCopy = {
                            viewModel.dispatch(
                                ScannerIntent.CopyResult
                            )
                        },
                        onShare = {
                            viewModel.dispatch(
                                ScannerIntent.ShareResult
                            )
                        },
                        onFavorite = {
                            viewModel.dispatch(
                                ScannerIntent.ToggleFavorite
                            )
                        },
                        onPrimaryAction = {
                            viewModel.dispatch(ScannerIntent.ScanAgain)
                        },
                        primaryActionText = "Scan Again"
                    )
                }
            }

            PermissionState.Denied -> {
                PermissionRequiredScreen(
                    onRequestPermission = {
                        permissionState.requestPermission()
                    }
                )
            }
            PermissionState.PermanentlyDenied -> {
                PermissionPermanentlyDeniedScreen()
            }
        }

    }

}