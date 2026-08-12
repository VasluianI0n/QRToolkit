package com.divergentapp.qrtoolkit.core.qr

import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.google.mlkit.vision.barcode.common.Barcode
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

internal fun Barcode.UrlBookmark.toContent() =
    QRContent.Url(
        url.orEmpty()
    )

internal fun Barcode.Email.toContent() =
    QRContent.Email(
        address = address.orEmpty(),
        subject = subject,
        body = body
    )

internal fun Barcode.Phone.toContent() =
    QRContent.Phone(
        number.orEmpty()
    )

internal fun Barcode.Sms.toContent() =
    QRContent.Sms(
        number = phoneNumber.orEmpty(),
        message = message
    )

internal fun Barcode.WiFi.toContent() =
    QRContent.Wifi(
        ssid = ssid.orEmpty(),
        password = password.orEmpty(),
        encryption = encryptionType.toEncryption(),
        hidden = false
    )

internal fun Barcode.GeoPoint.toContent() =
    QRContent.Location(
        latitude = lat.toString(),
        longitude = lng.toString()
    )

internal fun Barcode.ContactInfo.toContent() =
    QRContent.Contact(

        firstName = name?.first.orEmpty(),

        lastName = name?.last.orEmpty(),

        company = organization ?: "Unknown",

        phone = phones.firstOrNull()?.number ?: "Unknown",

        email = emails.firstOrNull()?.address ?: "Unknown"

    )

internal fun Barcode.CalendarEvent.toContent() =
    QRContent.Calendar(

        title = summary.orEmpty(),

        description = description ?: "",

        location = location ?: "Unknown",

        startDate = start?.toMillis().toString(),

        endDate = end?.toMillis().toString()

    )

internal fun Barcode.CalendarDateTime.toMillis(): Long {

    val calendar = if (isUtc) {
        Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    } else {
        Calendar.getInstance()
    }

    calendar.set(
        year,
        month - 1,
        day,
        hours,
        minutes,
        seconds
    )

    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.timeInMillis
}

internal fun Int.toEncryption(): String =
    when (this) {
        Barcode.WiFi.TYPE_OPEN -> "OPEN"
        Barcode.WiFi.TYPE_WEP -> "WEP"
        Barcode.WiFi.TYPE_WPA -> "WPA"
        else -> "OPEN"
    }