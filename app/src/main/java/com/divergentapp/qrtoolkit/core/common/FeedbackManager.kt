package com.divergentapp.qrtoolkit.core.common

import com.divergentapp.qrtoolkit.domain.model.Settings

class FeedbackManager(
    private val vibrationManager: VibrationManager,
    private val beepManager: BeepManager
) {

    fun play(settings: Settings) {
        if (settings.vibrateOnScan) {
            vibrationManager.vibrate()
        }

        if (settings.beepOnScan) {
            beepManager.playScanBeep()
        }
    }

    fun release(){
        beepManager.release()
    }
}