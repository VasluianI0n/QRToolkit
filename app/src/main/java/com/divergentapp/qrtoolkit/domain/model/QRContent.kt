package com.divergentapp.qrtoolkit.domain.model

import java.time.LocalDateTime

sealed interface QRContent {

    data class Text(
        val text: String
    ) : QRContent

    data class Url(
        val url: String
    ) : QRContent

    data class Wifi(
        val ssid: String = "",
        val password: String = "",
        val encryption: String = "WPA",
        val hidden: Boolean = false
    ) : QRContent

    data class Email(
        val address: String,
        val subject: String? = null,
        val body: String? = null
    ) : QRContent

    data class Phone(
        val number: String
    ) : QRContent

    data class Sms(
        val number: String,
        val message: String? = null
    ) : QRContent

    data class Contact(
        val firstName: String = "",
        val lastName: String = "",
        val company: String = "",
        val phone: String = "",
        val email: String = ""
    ) : QRContent

    data class Location(
        val latitude: String = "",
        val longitude: String = ""
    ) : QRContent

    data class Calendar(
        val title: String = "",
        val location: String = "",
        val description: String = "",
        val startDate: String = "",
        val startTime: String = "",
        val endDate: String = "",
        val endTime: String = ""
    ) : QRContent

    data class UploadedFile(
        val fileName: String = "",
        val url: String = ""
    ) : QRContent

    data class Raw(
        val value: String
    ) : QRContent

}