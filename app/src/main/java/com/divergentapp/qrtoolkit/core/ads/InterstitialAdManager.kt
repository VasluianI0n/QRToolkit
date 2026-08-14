package com.divergentapp.qrtoolkit.core.ads

import android.app.Activity
import android.util.Log
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdPreloader
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo

class InterstitialAdManager(
    private val adUnitId: String
) {

    companion object {
        private const val TAG = "InterstitialAdManager"
        private const val BUFFER_SIZE = 2
        private const val ACTIONS_BEFORE_AD = 3
        private const val COOLDOWN_MS = 3 * 60 * 1000L
    }

    private var actionCount = 3

    private var lastAdShownAt = 0L

    /**
     * Starts keeping interstitial ads preloaded.
     *
     * Call this once after Mobile Ads SDK initialization.
     */
    fun startPreloading() {

        val adRequest =
            AdRequest.Builder(adUnitId).build()

        val preloadConfig =
            PreloadConfiguration(
                adRequest,
                bufferSize = BUFFER_SIZE
            )

        InterstitialAdPreloader.start(
            adUnitId,
            preloadConfig,
            object : PreloadCallback {

                override fun onAdPreloaded(
                    preloadId: String,
                    responseInfo: ResponseInfo
                ) {

                    Log.d(
                        TAG,
                        "Interstitial preloaded: $preloadId"
                    )
                }

                override fun onAdFailedToPreload(
                    preloadId: String,
                    adError: LoadAdError
                ) {

                    Log.e(
                        TAG,
                        "Interstitial preload failed: " +
                                adError.message
                    )
                }

                override fun onAdsExhausted(
                    preloadId: String
                ) {

                    Log.d(
                        TAG,
                        "Interstitial cache exhausted"
                    )
                }
            }
        )
    }

    /**
     * Records a completed user action.
     *
     * Returns true when an interstitial should
     * be attempted.
     */
    fun recordAction(): Boolean {

        actionCount++

        Log.d(
            TAG,
            "Action count: $actionCount"
        )

        if (actionCount < ACTIONS_BEFORE_AD) {
            return false
        }

        if (!cooldownPassed()) {
            return false
        }

        actionCount = 0

        return true
    }

    /**
     * Attempts to show a preloaded interstitial.
     *
     * If no ad is available, nothing happens.
     */
    fun showIfAvailable(
        activity: Activity
    ) {

        val ad =
            InterstitialAdPreloader.pollAd(adUnitId)

        if (ad == null) {

            Log.d(
                TAG,
                "Interstitial not available"
            )

            return
        }

        lastAdShownAt =
            System.currentTimeMillis()

        ad.adEventCallback =
            object : InterstitialAdEventCallback {

                override fun onAdShowedFullScreenContent() {

                    Log.d(
                        TAG,
                        "Interstitial shown"
                    )
                }

                override fun onAdDismissedFullScreenContent() {

                    Log.d(
                        TAG,
                        "Interstitial dismissed"
                    )
                }

                override fun onAdFailedToShowFullScreenContent(
                    fullScreenContentError: FullScreenContentError
                ) {

                    Log.e(
                        TAG,
                        "Interstitial failed to show: " +
                                fullScreenContentError.message
                    )
                }

                override fun onAdImpression() {

                    Log.d(
                        TAG,
                        "Interstitial impression"
                    )
                }

                override fun onAdClicked() {

                    Log.d(
                        TAG,
                        "Interstitial clicked"
                    )
                }
            }

        ad.show(activity)
    }

    private fun cooldownPassed(): Boolean {

        val now =
            System.currentTimeMillis()

        return now - lastAdShownAt >= COOLDOWN_MS
    }

    fun destroy() {

        InterstitialAdPreloader.destroy(
            adUnitId
        )

        Log.d(
            TAG,
            "Interstitial preloading destroyed"
        )
    }
}