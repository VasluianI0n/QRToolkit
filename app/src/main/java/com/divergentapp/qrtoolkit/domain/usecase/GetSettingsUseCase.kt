package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<Settings> =
        repository.settings

}