package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.domain.repository.SettingsRepository

class SetBeepUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(
        enabled: Boolean
    ) {
        repository.setBeep(enabled)
    }

}