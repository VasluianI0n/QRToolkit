package com.divergentapp.qrtoolkit.core.camera

import androidx.camera.core.Camera

interface CameraController {

    fun attach(camera: Camera)

    fun setTorch(enabled: Boolean)

    fun toggleTorch()

    fun zoom(ratio: Float)

    fun resetZoom()

}