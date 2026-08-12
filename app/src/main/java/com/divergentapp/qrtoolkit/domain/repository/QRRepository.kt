package com.divergentapp.qrtoolkit.domain.repository

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import kotlinx.coroutines.flow.Flow

interface QRRepository {

    fun getHistory(): Flow<Resource<List<QRHistory>>>

    fun getFavorites(): Flow<Resource<List<QRHistory>>>

    fun searchHistory(
        query: String
    ): Flow<Resource<List<QRHistory>>>

    suspend fun saveHistory(
        history: QRHistory
    ): Resource<Long>

    suspend fun deleteHistory(
        id: Long
    ): Resource<Unit>

    suspend fun clearHistory(): Resource<Unit>

    suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    ): Resource<Unit>

}