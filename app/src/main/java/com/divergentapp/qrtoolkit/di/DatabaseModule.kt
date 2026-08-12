package com.divergentapp.qrtoolkit.di

import androidx.room.Room
import com.divergentapp.qrtoolkit.data.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {

        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "qr_toolkit.db"
        )
            .fallbackToDestructiveMigration(false)
            .build()

    }

    single {
        get<AppDatabase>().historyDao()
    }

}