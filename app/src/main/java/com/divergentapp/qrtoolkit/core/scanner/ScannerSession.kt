package com.divergentapp.qrtoolkit.core.scanner

interface ScannerSession {

    val isScanning: Boolean

    fun start()

    fun stop()

    fun pause()

    fun resume()

}