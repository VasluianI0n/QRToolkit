package com.divergentapp.qrtoolkit.core.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.appopen.AppOpenAdPreloader
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.common.PreloadCallback
import com.google.android.libraries.ads.mobile.sdk.common.PreloadConfiguration
import com.google.android.libraries.ads.mobile.sdk.common.ResponseInfo
import androidx.core.content.edit

class AppOpenAdManager(
    private val context: Context,
    private val adUnitId: String
) {

    companion object {
        private const val TAG = "AppOpenAdManager"

        private const val PREFS_NAME = "app_open_ad_prefs"
        private const val KEY_HAS_STARTED = "has_started"

    }

    private val preferences =
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

    private var currentActivity: Activity? = null

    private var pendingShow = false

    private var lastAdShownAt = 0L

    private val hasStartedBefore: Boolean
        get() = preferences.getBoolean(
            KEY_HAS_STARTED,
            false
        )

    private fun markAsStarted() {
        preferences
            .edit {
                putBoolean(KEY_HAS_STARTED, true)
            }
    }


    private var hasStarted = false

    /**
     * Start preloading App Open ads.
     *
     * Call this after MobileAds.initialize().
     */
    fun startPreloading() {

        val adRequest =
            AdRequest.Builder(adUnitId).build()

        val preloadConfiguration =
            PreloadConfiguration(
                adRequest,
                bufferSize = 1
            )

        AppOpenAdPreloader.start(
            adUnitId,
            preloadConfiguration,
            object : PreloadCallback {

                override fun onAdPreloaded(
                    preloadId: String,
                    responseInfo: ResponseInfo
                ) {

                    Log.d(
                        TAG,
                        "App Open ad preloaded: $preloadId"
                    )

                    tryShowPendingAd()
                }

                override fun onAdFailedToPreload(
                    preloadId: String,
                    adError: LoadAdError
                ) {

                    Log.e(
                        TAG,
                        "App Open preload failed: " +
                                adError.message
                    )
                }

                override fun onAdsExhausted(
                    preloadId: String
                ) {

                    Log.d(
                        TAG,
                        "App Open ad cache exhausted"
                    )
                }
            }
        )
    }

    /**
     * Called when the application enters the foreground.
     */
    fun onAppForegrounded(activity: Activity) {
        currentActivity = activity
        Log.d(
            TAG,
            "App foregrounded"
        )
        // First-ever launch: skip the ad.
        if (!hasStartedBefore) {
            markAsStarted()
            Log.d(
                TAG,
                "First launch - skipping App Open ad"
            )
            return
        }

        // We want an ad.
        pendingShow = true
        Log.d(
            TAG,
            "App Open ad requested"
        )
        tryShowPendingAd()
    }
    private fun tryShowPendingAd() {
        if (!pendingShow) return
        val activity = currentActivity

        if (activity == null) {
            Log.d(
                TAG,
                "No current Activity - waiting"
            )
            return
        }

        val ad = AppOpenAdPreloader.pollAd(adUnitId)

        if (ad == null) {
            Log.d(
                TAG,
                "No App Open ad available yet - waiting"
            )
            return
        }

        // Only clear this AFTER we actually got an ad.
        pendingShow = false

        lastAdShownAt =
            System.currentTimeMillis()

        ad.adEventCallback =
            object : AppOpenAdEventCallback {
                override fun onAdShowedFullScreenContent() {
                    Log.d(
                        TAG,
                        "App Open ad shown"
                    )
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d(
                        TAG,
                        "App Open ad dismissed"
                    )
                    startPreloading()
                }

                override fun onAdFailedToShowFullScreenContent(
                    fullScreenContentError: FullScreenContentError
                ) {
                    Log.e(
                        TAG,
                        "App Open ad failed: " +
                                fullScreenContentError.message
                    )
                    startPreloading()
                }

                override fun onAdImpression() {
                    Log.d(
                        TAG,
                        "App Open ad impression"
                    )
                }

                override fun onAdClicked() {
                    Log.d(
                        TAG,
                        "App Open ad clicked"
                    )
                }
            }
        ad.show(activity)
    }

    /**
     * Checks whether a preloaded App Open ad exists.
     */
    private fun isAdAvailable(): Boolean {
        return AppOpenAdPreloader.pollAd(
            adUnitId
        ) != null
    }

    /**
     * Shows a preloaded App Open ad.
     */
    fun showIfAvailable(
        activity: Activity
    ) {
        val ad =
            AppOpenAdPreloader.pollAd(
                adUnitId
            )
        if (ad == null) {

            Log.d(
                TAG,
                "No App Open ad available"
            )
            pendingShow = true
            return
        }

        /*
         * The ad is now consumed from the preloader.
         */
        lastAdShownAt =
            System.currentTimeMillis()

        ad.adEventCallback =
            object : AppOpenAdEventCallback {
                override fun onAdShowedFullScreenContent() {
                    Log.d(
                        TAG,
                        "App Open ad shown"
                    )
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d(
                        TAG,
                        "App Open ad dismissed"
                    )
                    startPreloading()
                }

                override fun onAdFailedToShowFullScreenContent(
                    fullScreenContentError: FullScreenContentError
                ) {
                    Log.e(
                        TAG,
                        "App Open ad failed to show: " +
                                fullScreenContentError.message
                    )
                    startPreloading()
                }

                override fun onAdImpression() {
                    Log.d(
                        TAG,
                        "App Open impression"
                    )
                }

                override fun onAdClicked() {
                    Log.d(
                        TAG,
                        "App Open clicked"
                    )
                }
            }

        ad.show(activity)
    }

    fun destroy() {

        AppOpenAdPreloader.destroy(
            adUnitId
        )

        currentActivity = null
        pendingShow = false

        Log.d(
            TAG,
            "App Open preloading destroyed"
        )
    }
}