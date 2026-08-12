package com.divergentapp.qrtoolkit.data.mapper

import com.divergentapp.qrtoolkit.core.qr.QRContentConverter
import com.divergentapp.qrtoolkit.data.local.entity.HistoryEntity
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.domain.model.QRFormat
import com.divergentapp.qrtoolkit.domain.model.QRHistory


class HistoryMapper(
    private val contentConverter: QRContentConverter
) {

    fun toDomain(entity: HistoryEntity): QRHistory =
        QRHistory(
            id = entity.id,
            content = contentConverter.fromJson(entity.contentJson),
            format = QRFormat.valueOf(entity.format),
            rawValue = entity.rawValue,
            createdAt = entity.createdAt,
            isFavorite = entity.isFavorite
        )

    fun toEntity(model: QRHistory): HistoryEntity =
        HistoryEntity(
            id = model.id,
            contentJson = contentConverter.toJson(model.content),
            format = model.format.name,
            rawValue = model.rawValue,
            createdAt = model.createdAt,
            isFavorite = model.isFavorite
        )

}