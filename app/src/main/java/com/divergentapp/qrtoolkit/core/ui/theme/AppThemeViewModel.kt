package com.divergentapp.qrtoolkit.core.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.usecase.GetSettingsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppThemeViewModel(

    getSettingsUseCase: GetSettingsUseCase

) : ViewModel() {

    val settings = getSettingsUseCase()

        .stateIn(

            scope = viewModelScope,

            started = SharingStarted.Eagerly,

            initialValue = Settings()

        )

}