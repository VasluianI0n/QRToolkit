package com.divergentapp.qrtoolkit.core.scanner

class ScannerSessionImpl : ScannerSession {

    override var isScanning = true
        private set

    override fun start() {
        isScanning = true
    }

    override fun stop() {
        isScanning = false
    }

    override fun pause() {
        isScanning = false
    }

    override fun resume() {
        isScanning = true
    }

}