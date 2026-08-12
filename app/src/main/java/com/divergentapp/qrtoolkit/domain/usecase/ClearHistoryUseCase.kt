package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.repository.QRRepository

class ClearHistoryUseCase(
    private val repository: QRRepository
) {

    suspend operator fun invoke(): Resource<Unit> {

        return repository.clearHistory()

    }
}