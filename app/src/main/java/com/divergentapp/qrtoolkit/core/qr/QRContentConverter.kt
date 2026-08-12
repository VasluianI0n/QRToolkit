package com.divergentapp.qrtoolkit.core.qr


import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.google.gson.Gson

class QRContentConverter(
    private val gson: Gson = Gson()
) {

    fun toJson(content: QRContent): String {

        val wrapper = QRContentWrapper(

            type = content::class.java.simpleName,

            data = gson.toJson(content)

        )

        return gson.toJson(wrapper)
    }

    fun fromJson(json: String): QRContent {

        val wrapper = gson.fromJson(
            json,
            QRContentWrapper::class.java
        )

        return when (wrapper.type) {

            QRContent.Text::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Text::class.java)

            QRContent.UploadedFile::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.UploadedFile::class.java)

            QRContent.Url::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Url::class.java)

            QRContent.Wifi::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Wifi::class.java)

            QRContent.Email::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Email::class.java)

            QRContent.Phone::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Phone::class.java)

            QRContent.Sms::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Sms::class.java)

            QRContent.Contact::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Contact::class.java)

            QRContent.Location::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Location::class.java)

            QRContent.Calendar::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Calendar::class.java)

            QRContent.Raw::class.java.simpleName ->
                gson.fromJson(wrapper.data, QRContent.Raw::class.java)

            else ->
                throw IllegalArgumentException(
                    "Unknown QRContent type: ${wrapper.type}"
                )
        }
    }
}