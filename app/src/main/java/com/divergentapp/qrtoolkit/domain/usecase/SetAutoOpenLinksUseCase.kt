package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.domain.repository.SettingsRepository

class SetAutoOpenLinksUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(
        enabled: Boolean
    ) {
        repository.setAutoOpenLinks(enabled)
    }

}