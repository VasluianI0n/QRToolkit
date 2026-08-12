package com.divergentapp.qrtoolkit.di

import com.divergentapp.qrtoolkit.core.camera.analyzer.QRAnalyzer
import com.divergentapp.qrtoolkit.core.qr.ParsedQR
import com.divergentapp.qrtoolkit.domain.usecase.ClearHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.DeleteHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.GenerateQRUseCase
import com.divergentapp.qrtoolkit.domain.usecase.GetHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.GetSettingsUseCase
import com.divergentapp.qrtoolkit.domain.usecase.HistoryUseCases
import com.divergentapp.qrtoolkit.domain.usecase.SaveHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.ScanQRUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetAutoOpenLinksUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetBeepUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetDarkThemeUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetUseSystemTheme
import com.divergentapp.qrtoolkit.domain.usecase.SetSaveGeneratedHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetSaveScannedHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SetVibrationUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SettingsUseCases
import com.divergentapp.qrtoolkit.domain.usecase.ToggleFavoriteUseCase
import com.divergentapp.qrtoolkit.domain.usecase.UpdateFavoriteUseCase
import com.divergentapp.qrtoolkit.domain.usecase.UploadFileUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory { SaveHistoryUseCase(get()) }
    factory { GetHistoryUseCase(get()) }
    factory { DeleteHistoryUseCase(get()) }
    factory { ClearHistoryUseCase(get()) }
    factory { UpdateFavoriteUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { GenerateQRUseCase(get(), get()) }
    factory { ScanQRUseCase() }
    factory { UploadFileUseCase(get()) }

    factory { (callback: (ParsedQR) -> Unit) ->
        QRAnalyzer(
            scanner = get(),
            parser = get(),
            session = get(),
            onResult = callback
        )
    }
    factory {
        HistoryUseCases(
            getHistory = get(),
            deleteHistory = get(),
            clearHistory = get(),
            toggleFavorite = get(),
            getSettings = get()
        )
    }

    factory {
        GetSettingsUseCase(get())
    }

    factory {
        SetDarkThemeUseCase(get())
    }

    factory {
        SetUseSystemTheme(get())
    }

    factory {
        SetVibrationUseCase(get())
    }

    factory {
        SetBeepUseCase(get())
    }

    factory {
        SetAutoOpenLinksUseCase(get())
    }

    factory {
        SetSaveGeneratedHistoryUseCase(get())
    }

    factory {
        SetSaveScannedHistoryUseCase(get())
    }

    factory {
        SettingsUseCases(
            getSettings = get(),
            setDarkTheme = get(),
            setUseSystemTheme = get(),
            setVibration = get(),
            setBeep = get(),
            setAutoOpenLinks = get(),
            setSaveGeneratedHistory = get(),
            setSaveScannedHistory = get(),
            clearHistory = get(),
        )
    }

}