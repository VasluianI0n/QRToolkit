package com.divergentapp.qrtoolkit.core.ads

import android.util.Log
import android.view.View
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.AdView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

@Composable
fun BannerAd(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val adUnitId: String = AdConfig.bannerAdUnitId(context)

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
            .animateContentSize()
    ) {

        val widthDp = maxWidth.value.toInt()

        var isLoaded by remember {
            mutableStateOf(false)
        }

        val adView = remember {
            AdView(context)
        }

        DisposableEffect(widthDp) {

            if (widthDp > 0) {

                val adSize =
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        widthDp
                    )

                val adRequest =
                    BannerAdRequest.Builder(
                        adUnitId,
                        adSize
                    ).build()

                Log.d(
                    "BannerAd",
                    "Loading banner. width=$widthDp"
                )

                adView.loadAd(
                    adRequest,
                    object : AdLoadCallback<BannerAd> {

                        override fun onAdLoaded(
                            ad: BannerAd
                        ) {
                            Log.d(
                                "BannerAd",
                                "Banner loaded successfully"
                            )

                            isLoaded = true
                        }

                        override fun onAdFailedToLoad(
                            adError: LoadAdError
                        ) {
                            Log.e(
                                "BannerAd",
                                "Banner failed to load: $adError"
                            )

                            isLoaded = false
                        }
                    }
                )
            }

            onDispose {
                adView.destroy()
            }
        }

        if (isLoaded) {

            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = {
                    adView
                },
                update = {
                    it.visibility = View.VISIBLE
                }
            )
        }
    }
}