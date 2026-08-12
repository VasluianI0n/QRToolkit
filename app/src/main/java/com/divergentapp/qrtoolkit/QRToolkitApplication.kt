package com.divergentapp.qrtoolkit

import android.app.Application
import com.divergentapp.qrtoolkit.di.appModule
import com.divergentapp.qrtoolkit.di.databaseModule
import com.divergentapp.qrtoolkit.di.networkModule
import com.divergentapp.qrtoolkit.di.repositoryModule
import com.divergentapp.qrtoolkit.di.useCaseModule
import com.divergentapp.qrtoolkit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class QRToolkitApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@QRToolkitApp)

            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}