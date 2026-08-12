package com.divergentapp.qrtoolkit.core.qr

import android.net.Uri

interface GalleryQrScanner {

    suspend fun scan(uri: Uri): ParsedQR?

}