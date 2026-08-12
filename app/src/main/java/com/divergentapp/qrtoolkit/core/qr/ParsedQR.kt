package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.domain.model.QRFormat

data class ParsedQR(

    val content: QRContent,

    val format: QRFormat,

    val rawValue: String

)