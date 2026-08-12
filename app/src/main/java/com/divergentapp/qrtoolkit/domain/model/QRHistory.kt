package com.divergentapp.qrtoolkit.domain.model

data class QRHistory(

    val id: Long = 0,

    val content: QRContent,

    val format: QRFormat,

    val rawValue: String,

    val createdAt: Long,

    val isFavorite: Boolean = false

)