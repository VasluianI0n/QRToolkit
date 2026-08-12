package com.divergentapp.qrtoolkit.core.ui.extensions

import androidx.compose.ui.graphics.vector.ImageVector
import com.divergentapp.qrtoolkit.core.ui.icons.alternate_email
import com.divergentapp.qrtoolkit.core.ui.icons.chat_bubble
import com.divergentapp.qrtoolkit.core.ui.icons.description
import com.divergentapp.qrtoolkit.core.ui.icons.event
import com.divergentapp.qrtoolkit.core.ui.icons.file_export
import com.divergentapp.qrtoolkit.core.ui.icons.language
import com.divergentapp.qrtoolkit.core.ui.icons.location_on
import com.divergentapp.qrtoolkit.core.ui.icons.person
import com.divergentapp.qrtoolkit.core.ui.icons.phone_callback
import com.divergentapp.qrtoolkit.core.ui.icons.picture_as_pdf
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code
import com.divergentapp.qrtoolkit.core.ui.icons.wifi
import com.divergentapp.qrtoolkit.domain.model.QRContent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun QRContent.title(): String =
    when (this) {

        is QRContent.Text -> "Text"

        is QRContent.Url -> "Website"

        is QRContent.Wifi -> "Wi-Fi Network"

        is QRContent.Email -> "Email"

        is QRContent.Phone -> "Phone Number"

        is QRContent.Sms -> "SMS"

        is QRContent.Contact -> "Contact"

        is QRContent.Location -> "Location"

        is QRContent.Calendar -> "Calendar Event"

        is QRContent.Raw -> "Raw Data"

        is QRContent.UploadedFile -> "Uploaded File"
    }

fun QRContent.description(): String =
    when (this) {

        is QRContent.Text ->
            text

        is QRContent.Url ->
            url

        is QRContent.Phone ->
            number

        is QRContent.Email ->
            buildString {

                append(address)

                subject?.let {
                    append("\nSubject: ")
                    append(it)
                }

                body?.let {
                    append("\n\n")
                    append(it)
                }

            }

        is QRContent.Wifi ->
            buildString {

                append("SSID: ")
                append(ssid)

                append("\nEncryption: ")
                append(encryption)

                if (password.isNotBlank()) {
                    append("\nPassword: ")
                    append(password)
                }

                append("\nHidden: ")
                append(if (hidden) "Yes" else "No")

            }

        is QRContent.Sms ->
            buildString {

                append(number)

                message?.takeIf { it.isNotBlank() }?.let {
                    append("\n\n")
                    append(it)
                }

            }

        is QRContent.Contact ->
            buildString {

                append(firstName)

                if (lastName.isNotBlank()) {
                    append(" ")
                    append(lastName)
                }

                company.let {
                    append("\n")
                    append(it)
                }

                phone?.let {
                    append("\nPhone: ")
                    append(it)
                }

                email?.let {
                    append("\nEmail: ")
                    append(it)
                }

            }

        is QRContent.Location ->
            "Latitude: $latitude\nLongitude: $longitude"

        is QRContent.Calendar ->
            buildString {

                append(title)

                description?.let {
                    append("\n")
                    append(it)
                }

                location?.let {
                    append("\nLocation: ")
                    append(it)
                }

            }

        is QRContent.Raw ->
            value

        is QRContent.UploadedFile -> fileName
    }

fun QRContent.displayType(): String =
    when (this) {
        is QRContent.Text -> "Text"
        is QRContent.Url -> "Website"
        is QRContent.Wifi -> "Wi-Fi"
        is QRContent.Email -> "Email"
        is QRContent.Phone -> "Phone"
        is QRContent.Sms -> "SMS"
        is QRContent.Contact -> "Contact"
        is QRContent.Location -> "Location"
        is QRContent.Calendar -> "Calendar"
        is QRContent.Raw -> "Raw"
        is QRContent.UploadedFile -> "Uploaded File"
    }

fun QRContent.icon(): ImageVector =
    when (this) {
        is QRContent.Text ->
            description
        is QRContent.Url ->
            language
        is QRContent.Wifi ->
            wifi
        is QRContent.Email ->
            alternate_email
        is QRContent.Phone ->
            phone_callback
        is QRContent.Sms ->
            chat_bubble
        is QRContent.Contact ->
            person
        is QRContent.Location ->
            location_on
        is QRContent.Calendar ->
            event
        is QRContent.Raw ->
            qr_code
        is QRContent.UploadedFile ->
            file_export
    }

private val historyFormatter by lazy {
    SimpleDateFormat(
        "dd MMM yyyy • HH:mm",
        Locale.getDefault()
    )
}

fun Long.formatHistoryDate(): String {
    return historyFormatter.format(Date(this))
}