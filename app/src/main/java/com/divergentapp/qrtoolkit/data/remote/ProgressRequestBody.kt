package com.divergentapp.qrtoolkit.data.remote

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.ByteArrayInputStream
import java.io.File

class ProgressRequestBody(
    private val file: File,
    private val contentType: MediaType,
    private val listener: (Int) -> Unit
) : RequestBody() {

    override fun contentType(): MediaType = contentType

    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = contentLength()

        file.inputStream().use { input ->

            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

            var uploaded = 0L
            var read: Int

            while (input.read(buffer).also { read = it } != -1) {
                sink.write(buffer, 0, read)

                uploaded += read

                listener(((uploaded * 100) / length).toInt())
            }
        }
    }

}