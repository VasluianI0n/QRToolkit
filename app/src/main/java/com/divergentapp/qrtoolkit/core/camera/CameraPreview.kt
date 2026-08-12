package com.divergentapp.qrtoolkit.core.camera


import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.divergentapp.qrtoolkit.core.camera.analyzer.QRAnalyzer

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    analyzer: QRAnalyzer,
    controller: CameraController
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember(context) {
        PreviewView(context).apply {
            implementationMode =
                PreviewView.ImplementationMode.PERFORMANCE
            scaleType =
                PreviewView.ScaleType.FILL_CENTER
        }
    }

    DisposableEffect(
        previewView,
        lifecycleOwner,
        analyzer) {

        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(context)

        val executor =
            ContextCompat.getMainExecutor(context)

        cameraProviderFuture.addListener({
            runCatching {
                val cameraProvider = cameraProviderFuture.get()

                val selector = when {
                    cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ->
                        CameraSelector.DEFAULT_BACK_CAMERA

                    cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ->
                        CameraSelector.DEFAULT_FRONT_CAMERA

                    else -> {
                        Log.e("CameraPreview", "No cameras available")
                        return@addListener
                    }
                }

                bindCamera(
                    context = context,
                    previewView = previewView,
                    lifecycleOwner = lifecycleOwner,
                    cameraProvider = cameraProvider,
                    analyzer = analyzer,
                    controller = controller,
                    selector = selector
                )
            }.onFailure {
                Log.e("CameraPreview", "Failed to initialize CameraX", it)
            }

        }, executor)

        onDispose {
            if (cameraProviderFuture.isDone) {
                runCatching {
                    cameraProviderFuture.get().unbindAll()
                }.onFailure {
                    Log.e("CameraPreview", "Failed to unbind camera", it)
                }
            }
        }

    }

    AndroidView(
        modifier = modifier,
        factory = {
            previewView
        }
    )

}

private fun bindCamera(
    context: Context,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraProvider: ProcessCameraProvider,
    analyzer: QRAnalyzer,
    controller: CameraController,
    selector: CameraSelector
) {
    runCatching {
        cameraProvider.unbindAll()
        val preview = Preview.Builder().build()
        preview.surfaceProvider = previewView.surfaceProvider

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(
                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
            )
            .build()

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(context),
            analyzer
        )

        val camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            selector,
            preview,
            imageAnalysis
        )

        controller.attach(camera)
    }.onFailure {
        Log.e("CameraPreview", "bindCamera failed", it)
    }
}