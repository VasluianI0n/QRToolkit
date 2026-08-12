package com.divergentapp.qrtoolkit.di

import com.divergentapp.qrtoolkit.data.remote.fileio.TmpFilesApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .build()
    }

    single(named("tmpfiles")) {
        Retrofit.Builder()
            .baseUrl("https://tmpfiles.org/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<TmpFilesApi> {
        get<Retrofit>(named("tmpfiles"))
            .create(TmpFilesApi::class.java)
    }
}