package com.divergentapp.qrtoolkit.core.util

import com.divergentapp.qrtoolkit.domain.model.QRContent

object GenerateValidator {

    fun canGenerate(
        content: QRContent
    ): Boolean {

        return when (content) {

            is QRContent.Text ->
                content.text.isNotBlank()

            is QRContent.Url ->
                content.url.isNotBlank()

            is QRContent.Phone ->
                content.number.isNotBlank()

            is QRContent.Sms ->
                content.number.isNotBlank()

            is QRContent.Email ->
                content.address.isNotBlank()

            is QRContent.Wifi ->
                content.ssid.isNotBlank()

            is QRContent.Location ->
                content.latitude.isNotBlank() &&
                        content.longitude.isNotBlank()

            is QRContent.Contact ->
                content.firstName.isNotBlank() ||
                        content.lastName.isNotBlank()

            is QRContent.UploadedFile ->
                content.url.isNotBlank()

            is QRContent.Calendar -> {

                content.title.isNotBlank() &&
                        content.startDate.isNotBlank() &&
                        content.startTime.isNotBlank() &&
                        content.endDate.isNotBlank() &&
                        content.endTime.isNotBlank()

            }

            else ->
                false

        }

    }

}