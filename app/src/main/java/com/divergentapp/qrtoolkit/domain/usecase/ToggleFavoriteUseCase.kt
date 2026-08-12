package com.divergentapp.qrtoolkit.domain.usecase

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.repository.QRRepository

class ToggleFavoriteUseCase(
    private val repository: QRRepository
) {

    suspend operator fun invoke(
        id: Long,
        favorite: Boolean
    ): Resource<Unit> {

        return repository.updateFavorite(
            id = id,
            favorite = favorite
        )

    }

}