package com.divergentapp.qrtoolkit.core.common

import android.media.AudioManager
import android.media.ToneGenerator

class BeepManager {

    private val toneGenerator = ToneGenerator(
        AudioManager.STREAM_MUSIC,
        100
    )

    fun playScanBeep() {
        toneGenerator.startTone(
            ToneGenerator.TONE_PROP_BEEP2,
            500
        )
    }

    fun release() {
        toneGenerator.release()
    }
}