package com.divergentapp.qrtoolkit.features.scanner.model

import com.divergentapp.qrtoolkit.core.mvi.BaseScreenState
import com.divergentapp.qrtoolkit.core.mvi.UiEffect
import com.divergentapp.qrtoolkit.core.mvi.UiIntent
import com.divergentapp.qrtoolkit.core.permission.PermissionState
import com.divergentapp.qrtoolkit.core.qr.ParsedQR
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.domain.model.QRFormat

sealed interface ScannerIntent : UiIntent {

    data object StartScanning : ScannerIntent

    data object StopScanning : ScannerIntent

    data object ToggleFlash : ScannerIntent

    data object OpenGallery : ScannerIntent

    data class QRDetected(

        val parsedQR: ParsedQR

    ) : ScannerIntent

    data object DismissResult : ScannerIntent

    data object ScanAgain : ScannerIntent

    data object OpenResult : ScannerIntent

    data object CopyResult : ScannerIntent

    data object ShareResult : ScannerIntent

    data object ToggleFavorite : ScannerIntent

    data object GalleryScanFailed : ScannerIntent

}
data class ScannerState(

    override val isLoading: Boolean = false,

    override val error: String? = null,

    val isScanning: Boolean = true,

    val flashEnabled: Boolean = false,

    val scannedQr: ParsedQR? = null

) :  BaseScreenState(
    isLoading,
    error
)

sealed interface ScannerEffect : UiEffect{

    data object OpenGalleryPicker : ScannerEffect

    data class Open(
        val content: QRContent
    ) : ScannerEffect

    data class Copy(
        val rawValue: String
    ) : ScannerEffect

    data class Share(
        val rawValue: String
    ) : ScannerEffect

    data class ShowMessage(
        val message: String
    ) : ScannerEffect

}