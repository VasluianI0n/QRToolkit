package com.divergentapp.qrtoolkit

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.divergentapp.qrtoolkit.core.ads.AppOpenAdManager
import com.divergentapp.qrtoolkit.core.ads.InterstitialAdManager
import com.divergentapp.qrtoolkit.di.adsModule
import com.divergentapp.qrtoolkit.di.databaseModule
import com.divergentapp.qrtoolkit.di.networkModule
import com.divergentapp.qrtoolkit.di.repositoryModule
import com.divergentapp.qrtoolkit.di.useCaseModule
import com.divergentapp.qrtoolkit.di.viewModelModule
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

class QRToolkitApp : Application(),
    DefaultLifecycleObserver,
    Application.ActivityLifecycleCallbacks {

    private val applicationScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO
        )

    private var currentActivity: Activity? = null

    private var isAppInForeground = false

    private val _adsInitialized =
        MutableStateFlow(false)

    val adsInitialized =
        _adsInitialized.asStateFlow()

    private val appOpenAdManager: AppOpenAdManager
        get() = getKoin().get()

    private val interstitialAdManager: InterstitialAdManager
        get() = getKoin().get()

    override fun onCreate() {
        super<Application>.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@QRToolkitApp)

            modules(
                networkModule,
                databaseModule,
                adsModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }

        registerActivityLifecycleCallbacks(this)

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(this)

        initializeAds()
    }

    private fun initializeAds() {

        applicationScope.launch {

            val initializationConfig =
                InitializationConfig.Builder(
                    getString(R.string.admob_app_id)
                ).build()

            MobileAds.initialize(
                this@QRToolkitApp,
                initializationConfig
            ) {

                interstitialAdManager.startPreloading()

                appOpenAdManager.startPreloading()

                _adsInitialized.value = true

                /*
                 * The Activity may already be visible by the time
                 * Mobile Ads finishes initialization.
                 */
                if (isAppInForeground) {

                    currentActivity?.let { activity ->

                        appOpenAdManager.onAppForegrounded(
                            activity
                        )
                    }
                }
            }
        }
    }

    // ---------------------------------------------------------
    // APP FOREGROUND
    // ---------------------------------------------------------

    override fun onStart(owner: LifecycleOwner) {

        isAppInForeground = true

        if (!_adsInitialized.value) {
            return
        }

        currentActivity?.let { activity ->

            appOpenAdManager.onAppForegrounded(
                activity
            )
        }
    }

    // ---------------------------------------------------------
    // APP BACKGROUND
    // ---------------------------------------------------------

    override fun onStop(owner: LifecycleOwner) {

        isAppInForeground = false
    }

    // ---------------------------------------------------------
    // ACTIVITY LIFECYCLE
    // ---------------------------------------------------------

    override fun onActivityStarted(
        activity: Activity
    ) {
        currentActivity = activity
    }

    override fun onActivityResumed(
        activity: Activity
    ) {
    }

    override fun onActivityDestroyed(
        activity: Activity
    ) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) = Unit

    override fun onActivityPaused(
        activity: Activity
    ) = Unit

    override fun onActivityStopped(
        activity: Activity
    ) = Unit

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle
    ) = Unit
}