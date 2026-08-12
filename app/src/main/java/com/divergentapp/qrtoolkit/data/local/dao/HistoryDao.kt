package com.divergentapp.qrtoolkit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.divergentapp.qrtoolkit.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("""
        SELECT * FROM history
        WHERE isFavorite = 1
        ORDER BY createdAt DESC
    """)
    fun getFavorites(): Flow<List<HistoryEntity>>

    @Query("""
            SELECT *
            FROM history
            WHERE rawValue LIKE '%' || :query || '%'
               OR contentJson LIKE '%' || :query || '%'
            ORDER BY createdAt DESC
            """)
    fun search(query: String): Flow<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryEntity): Long

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM history")
    suspend fun clear()

    @Query("UPDATE history SET isFavorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(
        id: Long,
        favorite: Boolean
    )
}