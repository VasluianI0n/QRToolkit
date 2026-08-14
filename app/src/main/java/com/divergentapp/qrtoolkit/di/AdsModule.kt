package com.divergentapp.qrtoolkit.di

import com.divergentapp.qrtoolkit.core.ads.AdConfig
import com.divergentapp.qrtoolkit.core.ads.AppOpenAdManager
import com.divergentapp.qrtoolkit.core.ads.ConsentManager
import com.divergentapp.qrtoolkit.core.ads.InterstitialAdManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val adsModule = module {

    single { ConsentManager(androidContext()) }

    single {
        AppOpenAdManager(
            androidContext(),
            AdConfig.appOpenAdUnitId(
                androidContext()
            )
        )
    }

    single<InterstitialAdManager> {
        InterstitialAdManager(
            adUnitId = AdConfig.interstitialAdUnitId(androidContext())
        )
    }
}