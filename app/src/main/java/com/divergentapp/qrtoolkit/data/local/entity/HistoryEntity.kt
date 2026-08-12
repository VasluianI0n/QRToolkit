package com.divergentapp.qrtoolkit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history")
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val contentJson: String,

    val format: String,

    val rawValue: String,

    val createdAt: Long,

    val isFavorite: Boolean

)