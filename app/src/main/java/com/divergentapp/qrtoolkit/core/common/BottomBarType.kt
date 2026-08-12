package com.divergentapp.qrtoolkit.core.common

import androidx.compose.ui.graphics.vector.ImageVector
import com.divergentapp.qrtoolkit.core.ui.icons.history
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code_scanner
import com.divergentapp.qrtoolkit.core.ui.icons.settings

enum class BottomBarType(val icon: ImageVector) {
    SCAN_QR(qr_code_scanner),
    GENERATE_QR(qr_code),
    HISTORY(history),
    SETTINGS(settings)
}