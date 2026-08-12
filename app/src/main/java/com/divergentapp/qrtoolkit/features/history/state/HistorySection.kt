package com.divergentapp.qrtoolkit.features.history.state

import com.divergentapp.qrtoolkit.domain.model.QRHistory

data class HistorySection(
    val title: String,
    val items: List<QRHistory>
)