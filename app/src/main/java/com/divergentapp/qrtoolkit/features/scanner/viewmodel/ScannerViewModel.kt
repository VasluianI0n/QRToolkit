package com.divergentapp.qrtoolkit.features.scanner.viewmodel

import androidx.lifecycle.viewModelScope
import com.divergentapp.qrtoolkit.core.camera.CameraController
import com.divergentapp.qrtoolkit.core.mvi.BaseViewModel
import com.divergentapp.qrtoolkit.core.scanner.ScannerSession
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.usecase.GetSettingsUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SaveHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.ToggleFavoriteUseCase
import com.divergentapp.qrtoolkit.features.scanner.model.ScannerEffect
import com.divergentapp.qrtoolkit.features.scanner.model.ScannerIntent
import com.divergentapp.qrtoolkit.features.scanner.model.ScannerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ScannerViewModel(
    private val saveHistoryUseCase: SaveHistoryUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val cameraController: CameraController,
    private val scannerSession: ScannerSession,
    private val getSettingsUseCase: GetSettingsUseCase
) : BaseViewModel<ScannerIntent, ScannerState, ScannerEffect>(
    ScannerState()
) {

    companion object {
        private const val SCAN_DEBOUNCE_MS = 1_500L
    }

    private var lastScannedValue: String? = null

    private var lastScanTimestamp: Long = 0L
    private var currentHistoryId: Long? = null
    private var isCurrentFavorite = false

    val settings = getSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    override fun onIntent(intent: ScannerIntent) {

        when (intent) {

            ScannerIntent.StartScanning -> startScanning()

            ScannerIntent.StopScanning -> stopScanning()

            ScannerIntent.ToggleFlash -> toggleFlash()

            ScannerIntent.OpenGallery -> openGallery()

            ScannerIntent.DismissResult -> dismissResult()

            ScannerIntent.ScanAgain -> scanAgain()

            ScannerIntent.CopyResult -> copyResult()

            ScannerIntent.ShareResult -> shareResult()

            ScannerIntent.OpenResult -> openResult()

            ScannerIntent.ToggleFavorite -> toggleFavorite()

            ScannerIntent.GalleryScanFailed -> galleryScanFailed()

            is ScannerIntent.QRDetected -> onQrDetected(intent)

        }

    }

    private fun startScanning() {
        scannerSession.resume()
        setState {
            copy(
                isScanning = true
            )
        }
    }

    private fun stopScanning() {
        scannerSession.pause()
        setState {
            copy(
                isScanning = false
            )
        }
    }

    private fun galleryScanFailed() {
        sendEffect(
            ScannerEffect.ShowMessage(
                "No QR code found."
            )
        )
    }

    private fun toggleFlash() {
        val enabled = !state.value.flashEnabled
        cameraController.setTorch(enabled)
        setState {
            copy(
                flashEnabled = enabled
            )
        }
    }

    private fun onQrDetected(
        intent: ScannerIntent.QRDetected
    ) {
        if (!state.value.isScanning) {
            return
        }
        val now = System.currentTimeMillis()
        val parsedQr = intent.parsedQR
        if (
            parsedQr.rawValue == lastScannedValue &&
            now - lastScanTimestamp < SCAN_DEBOUNCE_MS
        ) {
            return
        }
        lastScannedValue = parsedQr.rawValue
        lastScanTimestamp = now
        scannerSession.pause()
        launch {
            if (settings.value.saveScannedHistory) {
                handleResource(
                    resource = saveHistoryUseCase(
                        QRHistory(
                            content = parsedQr.content,
                            format = parsedQr.format,
                            rawValue = parsedQr.rawValue,
                            createdAt = System.currentTimeMillis()
                        )
                    ),
                    onSuccess = { id ->

                        currentHistoryId = id
                        isCurrentFavorite = false

                        setState {
                            copy(
                                isScanning = false,
                                scannedQr = parsedQr
                            )
                        }
                    }
                )
            }
        }
    }

    private fun dismissResult() {
        scannerSession.resume()
        setState {
            copy(
                isScanning = true,
                scannedQr = null
            )
        }
    }

    private fun scanAgain() {
        lastScannedValue = null
        lastScanTimestamp = 0L
        scannerSession.resume()
        setState {
            copy(
                isScanning = true,
                scannedQr = null
            )
        }
    }

    private fun openGallery() {
        sendEffect(
            ScannerEffect.OpenGalleryPicker
        )
    }

    private fun openResult() {
        state.value.scannedQr?.let {
            sendEffect(
                ScannerEffect.Open(it.content)
            )
        }
    }

    private fun copyResult() {
        state.value.scannedQr?.let {
            sendEffect(
                ScannerEffect.Copy(it.rawValue)
            )
        }
    }

    private fun shareResult() {
        state.value.scannedQr?.let {
            sendEffect(
                ScannerEffect.Share(it.rawValue)
            )
        }
    }

    private fun toggleFavorite() {

        val id = currentHistoryId ?: return

        launch {

            handleResource(
                resource = toggleFavoriteUseCase(
                    id = id,
                    favorite = !isCurrentFavorite
                ),
                onSuccess = {

                    isCurrentFavorite = !isCurrentFavorite

                    sendEffect(
                        ScannerEffect.ShowMessage(
                            if (isCurrentFavorite)
                                "Added to favorites"
                            else
                                "Removed from favorites"
                        )
                    )
                }
            )

        }
    }

    override fun onLoading(loading: Boolean) {
        setState {
            copy(
                isLoading = loading
            )
        }
    }

    override fun onError(message: String?) {
        setState {
            copy(
                error = message
            )
        }
    }

}