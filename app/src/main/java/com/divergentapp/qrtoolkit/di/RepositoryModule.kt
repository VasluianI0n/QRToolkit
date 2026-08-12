package com.divergentapp.qrtoolkit.di

import com.divergentapp.qrtoolkit.core.common.BeepManager
import com.divergentapp.qrtoolkit.core.camera.CameraController
import com.divergentapp.qrtoolkit.core.camera.CameraControllerImpl
import com.divergentapp.qrtoolkit.core.common.FeedbackManager
import com.divergentapp.qrtoolkit.core.common.VibrationManager
import com.divergentapp.qrtoolkit.core.datastore.DataStoreManager
import com.divergentapp.qrtoolkit.core.datastore.SettingsDataStore
import com.divergentapp.qrtoolkit.core.qr.GalleryQrScanner
import com.divergentapp.qrtoolkit.core.qr.GalleryQrScannerImpl
import com.divergentapp.qrtoolkit.core.qr.QRAnalyzerFactory
import com.divergentapp.qrtoolkit.core.qr.QRAnalyzerFactoryImpl
import com.divergentapp.qrtoolkit.core.qr.QRCodeGenerator
import com.divergentapp.qrtoolkit.core.qr.QRContentConverter
import com.divergentapp.qrtoolkit.core.qr.QRContentEncoder
import com.divergentapp.qrtoolkit.core.qr.QRParser
import com.divergentapp.qrtoolkit.core.qr.QRParserImpl
import com.divergentapp.qrtoolkit.core.qr.ZXingQRCodeGenerator
import com.divergentapp.qrtoolkit.core.scanner.ScannerSession
import com.divergentapp.qrtoolkit.core.scanner.ScannerSessionImpl
import com.divergentapp.qrtoolkit.data.mapper.HistoryMapper
import com.divergentapp.qrtoolkit.data.repository.FileUploadRepositoryImpl
import com.divergentapp.qrtoolkit.data.repository.QRRepositoryImpl
import com.divergentapp.qrtoolkit.data.repository.SettingsRepositoryImpl
import com.divergentapp.qrtoolkit.domain.repository.FileUploadRepository
import com.divergentapp.qrtoolkit.domain.repository.QRRepository
import com.divergentapp.qrtoolkit.domain.repository.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single {
        QRContentConverter()
    }

    single {
        HistoryMapper(get())
    }

    single<QRRepository> {
        QRRepositoryImpl(
            historyDao = get(),
            mapper = get()
        )
    }

    single<QRParser> {
        QRParserImpl()
    }

    single<ScannerSession> {
        ScannerSessionImpl()
    }

    single<QRAnalyzerFactory> {
        QRAnalyzerFactoryImpl(
            get(),
            get()
        )
    }

    single<CameraController> {
        CameraControllerImpl()
    }



    single {
        DataStoreManager(get())
    }

    single {
        SettingsDataStore(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<GalleryQrScanner> {
        GalleryQrScannerImpl(
            context = androidContext(),
            qrParser = get()
        )
    }

    single<QRCodeGenerator> {
        ZXingQRCodeGenerator()
    }

    single {
        QRContentEncoder()
    }

    single<FileUploadRepository>{
        FileUploadRepositoryImpl(get())
    }

    single {
        VibrationManager(androidContext())
    }

    single {
        BeepManager()
    }

    single {
        FeedbackManager(get(), get())
    }
}