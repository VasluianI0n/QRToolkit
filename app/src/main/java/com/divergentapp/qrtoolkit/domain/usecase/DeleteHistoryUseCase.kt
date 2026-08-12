package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.repository.QRRepository


class DeleteHistoryUseCase(
    private val repository: QRRepository
) {

    suspend operator fun invoke(
        id: Long
    ): Resource<Unit> {

        return repository.deleteHistory(id)

    }
}