package com.divergentapp.qrtoolkit.di

import com.divergentapp.qrtoolkit.core.ui.theme.AppThemeViewModel
import com.divergentapp.qrtoolkit.features.generator.viewmodel.GenerateViewModel
import com.divergentapp.qrtoolkit.features.history.viewmodel.HistoryViewModel
import com.divergentapp.qrtoolkit.features.scanner.viewmodel.ScannerViewModel
import com.divergentapp.qrtoolkit.features.settings.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ScannerViewModel(get(), get(), get(), get(), get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { GenerateViewModel(get(), get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { AppThemeViewModel(get()) }
}