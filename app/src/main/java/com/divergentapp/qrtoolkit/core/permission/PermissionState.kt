package com.divergentapp.qrtoolkit.core.permission

sealed interface PermissionState {
    data object Unknown : PermissionState
    data object Granted : PermissionState
    data object Denied : PermissionState
    data object PermanentlyDenied : PermissionState

}