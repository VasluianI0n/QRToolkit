package com.divergentapp.qrtoolkit.core.ads

import android.content.Context
import com.divergentapp.qrtoolkit.R

object AdConfig {
    fun bannerAdUnitId(context: Context): String {
        return context.getString(R.string.admob_banner_id)
    }

    fun interstitialAdUnitId(context: Context): String {
        return context.getString(R.string.admob_interstitial_id)
    }

    fun appOpenAdUnitId(context: Context): String {
        return context.getString(R.string.admob_app_open)
    }
}