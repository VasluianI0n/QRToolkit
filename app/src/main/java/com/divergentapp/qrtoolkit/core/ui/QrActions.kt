package com.divergentapp.qrtoolkit.core.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Toast
import com.divergentapp.qrtoolkit.domain.model.QRContent
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.copyQr(value: String) {

    val clipboard = getSystemService(ClipboardManager::class.java)

    clipboard.setPrimaryClip(
        ClipData.newPlainText(
            "QR Code",
            value
        )
    )

    Toast.makeText(
        this,
        "Copied to clipboard",
        Toast.LENGTH_SHORT
    ).show()

}

fun Context.shareQr(value: String) {

    startActivity(

        Intent.createChooser(

            Intent(Intent.ACTION_SEND).apply {

                type = "text/plain"

                putExtra(
                    Intent.EXTRA_TEXT,
                    value
                )

            },

            null

        )

    )

}

fun Context.openQrContent(
    content: QRContent
) {

    when (content) {

        is QRContent.Url -> {

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(content.url)
                )
            )

        }

        is QRContent.Phone -> {

            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${content.number}")
                )
            )

        }

        is QRContent.Email -> {

            startActivity(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("mailto:${content.address}")
                ).apply {

                    content.subject?.let {
                        putExtra(Intent.EXTRA_SUBJECT, it)
                    }

                    content.body?.let {
                        putExtra(Intent.EXTRA_TEXT, it)
                    }

                }

            )

        }

        is QRContent.Sms -> {

            startActivity(
                Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("smsto:${content.number}")
                ).apply {

                    content.message?.let {
                        putExtra("sms_body", it)
                    }

                }

            )

        }

        is QRContent.Location -> {

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:${content.latitude},${content.longitude}")
                )
            )

        }

        is QRContent.Wifi -> {
            connectToWifi(content)
        }

        is QRContent.Contact -> {
            addContact(content)
        }

        is QRContent.Calendar -> {
            addCalendarEvent(content)
        }

        is QRContent.Text,
        is QRContent.Raw -> {

            Toast.makeText(
                this,
                "Nothing to open for this QR code.",
                Toast.LENGTH_SHORT
            ).show()

        }

        is QRContent.UploadedFile -> {

        }
    }

}

private fun Context.addContact(
    contact: QRContent.Contact
) {

    val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {

        type = ContactsContract.RawContacts.CONTENT_TYPE

        putExtra(
            ContactsContract.Intents.Insert.NAME,
            "${contact.firstName} ${contact.lastName}".trim()
        )

        putExtra(
            ContactsContract.Intents.Insert.COMPANY,
            contact.company
        )

        putExtra(
            ContactsContract.Intents.Insert.PHONE,
            contact.phone
        )

        putExtra(
            ContactsContract.Intents.Insert.EMAIL,
            contact.email
        )

        putExtra(
            ContactsContract.Intents.Insert.POSTAL,
            contact.email
        )

    }

    startActivity(intent)

}
private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm"

private fun String.toCalendarMillis(): Long? {
    return try {
        val formatter = SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault())
        formatter.parse(this)?.time
    } catch (e: Exception) {
        null
    }
}
private fun Context.addCalendarEvent(
    event: QRContent.Calendar
) {

    val startMillis = "${event.startDate} ${event.startTime}".toCalendarMillis()
    val endMillis = "${event.endDate} ${event.endTime}".toCalendarMillis()

    val intent = Intent(Intent.ACTION_INSERT).apply {

        data = CalendarContract.Events.CONTENT_URI

        putExtra(CalendarContract.Events.TITLE, event.title)
        putExtra(CalendarContract.Events.DESCRIPTION, event.description)
        putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)

        startMillis?.let {
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, it)
        }

        endMillis?.let {
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, it)
        }
    }

    startActivity(intent)
}

private fun Context.connectToWifi(
    wifi: QRContent.Wifi
) {

    val clipboard = getSystemService(ClipboardManager::class.java)

    clipboard.setPrimaryClip(
        ClipData.newPlainText(
            "Wi-Fi Password",
            wifi.password
        )
    )

    Toast.makeText(
        this,
        "Password copied to clipboard",
        Toast.LENGTH_SHORT
    ).show()

    val intent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent(Settings.Panel.ACTION_WIFI)
        } else {
            Intent(Settings.ACTION_WIFI_SETTINGS)
        }

    startActivity(intent)

}