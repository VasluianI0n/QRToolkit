package com.divergentapp.qrtoolkit.core.camera


import androidx.camera.core.Camera

class CameraControllerImpl : CameraController {

    private var camera: Camera? = null

    override fun attach(camera: Camera) {
        this.camera = camera
    }

    override fun setTorch(enabled: Boolean) {
        camera?.cameraControl?.enableTorch(enabled)
    }

    override fun toggleTorch() {

        val enabled =
            camera?.cameraInfo?.torchState?.value ==
                    androidx.camera.core.TorchState.ON

        camera?.cameraControl?.enableTorch(!enabled)
    }

    override fun zoom(ratio: Float) {
        camera?.cameraControl?.setZoomRatio(ratio)
    }

    override fun resetZoom() {
        camera?.cameraControl?.setLinearZoom(0f)
    }

}