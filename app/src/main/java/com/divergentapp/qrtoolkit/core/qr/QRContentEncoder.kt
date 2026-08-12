package com.divergentapp.qrtoolkit.core.qr

import android.net.Uri
import com.divergentapp.qrtoolkit.domain.model.QRContent

class QRContentEncoder {

    fun encode(
        content: QRContent
    ): String {

        return when (content) {

            is QRContent.Text ->
                content.text

            is QRContent.UploadedFile -> {

                val url = content.url.trim()

                if (
                    url.startsWith("http://") ||
                    url.startsWith("https://")
                ) {
                    url
                } else {
                    "https://$url"
                }

            }

            is QRContent.Url -> {

                val url = content.url.trim()

                if (
                    url.startsWith("http://") ||
                    url.startsWith("https://")
                ) {
                    url
                } else {
                    "https://$url"
                }

            }

            is QRContent.Location ->
                "https://maps.google.com/?q=${content.latitude},${content.longitude}"

            is QRContent.Phone -> {
                "tel:${content.number}"
            }

            is QRContent.Sms -> {
                val phone = content.number
                val message = Uri.encode(content.message)

                "SMSTO:$phone:$message"
            }

            is QRContent.Email -> {
                val subject = Uri.encode(content.subject)
                val body = Uri.encode(content.body)

                buildString {
                    append("mailto:")
                    append(content.address)

                    if (subject.isNotBlank() || body.isNotBlank()) {
                        append("?")
                        if (subject.isNotBlank()) {
                            append("subject=")
                            append(subject)
                        }

                        if (body.isNotBlank()) {
                            if (subject.isNotBlank())
                                append("&")
                            append("body=")
                            append(body)
                        }
                    }
                }
            }

            is QRContent.Calendar -> {

                val start = content.startDate.replace("-", "") +
                        "T" +
                        content.startTime.replace(":", "") +
                        "00"

                val end = content.endDate.replace("-", "") +
                        "T" +
                        content.endTime.replace(":", "") +
                        "00"

                buildString {

                    appendLine("BEGIN:VCALENDAR")
                    appendLine("VERSION:2.0")
                    appendLine("BEGIN:VEVENT")
                    appendLine("SUMMARY:${content.title}")

                    if (content.location.isNotBlank()) {
                        appendLine("LOCATION:${content.location}")
                    }

                    if (content.description.isNotBlank()) {
                        appendLine("DESCRIPTION:${content.description}")
                    }

                    appendLine("DTSTART:$start")
                    appendLine("DTEND:$end")
                    appendLine("END:VEVENT")
                    append("END:VCALENDAR")

                }

            }

            is QRContent.Contact -> {

                buildString {

                    appendLine("BEGIN:VCARD")
                    appendLine("VERSION:3.0")
                    appendLine("N:${content.lastName};${content.firstName}")
                    appendLine("FN:${content.firstName} ${content.lastName}")

                    if (content.company.isNotBlank())
                        appendLine("ORG:${content.company}")

                    if (content.phone.isNotBlank())
                        appendLine("TEL:${content.phone}")

                    if (content.email.isNotBlank())
                        appendLine("EMAIL:${content.email}")

                    append("END:VCARD")

                }

            }

            is QRContent.Wifi -> {

                buildString {

                    append("WIFI:")
                    append("T:${content.encryption};")
                    append("S:${content.ssid};")
                    append("P:${content.password};")
                    append(";")

                }

            }

            else ->
                throw IllegalArgumentException(
                    "Encoding not implemented for ${content::class.simpleName}"
                )
        }
    }
}