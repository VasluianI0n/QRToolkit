package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.repository.QRRepository


class SaveHistoryUseCase(
    private val repository: QRRepository
) {

    suspend operator fun invoke(
        history: QRHistory
    ): Resource<Long> {

        return repository.saveHistory(history)

    }
}