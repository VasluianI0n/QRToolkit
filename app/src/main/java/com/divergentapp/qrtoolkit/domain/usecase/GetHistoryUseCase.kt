package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.repository.QRRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCase(
    private val repository: QRRepository
) {

    operator fun invoke(): Flow<Resource<List<QRHistory>>> {
        return repository.getHistory()
    }
}