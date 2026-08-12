package com.divergentapp.qrtoolkit.features.generator.model

import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.features.generator.components.GenerateType

fun GenerateType.defaultContent(): QRContent =
    when (this) {

        GenerateType.TEXT ->
            QRContent.Text("")

        GenerateType.URL ->
            QRContent.Url("")

        GenerateType.WIFI ->
            QRContent.Wifi(
                ssid = "",
                password = "",
                encryption = "",
                hidden = false
            )

        GenerateType.EMAIL ->
            QRContent.Email(
                address = "",
                subject = "",
                body = ""
            )

        GenerateType.PHONE ->
            QRContent.Phone("")

        GenerateType.SMS ->
            QRContent.Sms(
                number = "",
                message = ""
            )

        GenerateType.CONTACT -> QRContent.Contact()

        GenerateType.LOCATION -> QRContent.Location()

        GenerateType.PDF ->  QRContent.UploadedFile()

        GenerateType.AUDIO -> QRContent.UploadedFile()

        GenerateType.VIDEO -> QRContent.UploadedFile()

        GenerateType.IMAGE -> QRContent.UploadedFile()

        GenerateType.CALENDAR -> QRContent.Calendar()
    }