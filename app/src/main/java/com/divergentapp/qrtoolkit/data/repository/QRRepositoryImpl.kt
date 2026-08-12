package com.divergentapp.qrtoolkit.data.repository

import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.core.common.asResource
import com.divergentapp.qrtoolkit.core.common.resourceOf
import com.divergentapp.qrtoolkit.data.local.dao.HistoryDao
import com.divergentapp.qrtoolkit.data.mapper.HistoryMapper
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.repository.QRRepository
import kotlinx.coroutines.flow.Flow

class QRRepositoryImpl(
    private val historyDao: HistoryDao,
    private val mapper: HistoryMapper
) : QRRepository {

    override fun getHistory(): Flow<Resource<List<QRHistory>>> {
        return historyDao
            .getHistory()
            .asResource { entities ->
                entities.map(mapper::toDomain)
            }
    }

    override fun getFavorites(): Flow<Resource<List<QRHistory>>> {
        return historyDao
            .getFavorites()
            .asResource { entities ->
                entities.map(mapper::toDomain)
            }
    }

    override fun searchHistory(
        query: String
    ): Flow<Resource<List<QRHistory>>> {
        return historyDao
            .search(query)
            .asResource { entities ->
                entities.map(mapper::toDomain)
            }
    }

    override suspend fun saveHistory(
        history: QRHistory
    ): Resource<Long> {
        return resourceOf {
            val entity = mapper.toEntity(history)
            historyDao.insert(entity)
        }
    }

    override suspend fun deleteHistory(
        id: Long
    ): Resource<Unit> {
        return resourceOf {
            historyDao.delete(id)
        }
    }

    override suspend fun clearHistory(): Resource<Unit> {
        return resourceOf {
            historyDao.clear()
        }
    }

    override suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    ): Resource<Unit> {
        return resourceOf {
            historyDao.updateFavorite(
                id = id,
                favorite = favorite
            )
        }
    }
}