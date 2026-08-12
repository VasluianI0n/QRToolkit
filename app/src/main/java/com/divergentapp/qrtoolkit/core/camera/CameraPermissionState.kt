package com.divergentapp.qrtoolkit.core.camera

import com.divergentapp.qrtoolkit.core.permission.PermissionState

interface CameraPermissionState {

    val permission: PermissionState

    fun requestPermission()

    fun refresh()

}