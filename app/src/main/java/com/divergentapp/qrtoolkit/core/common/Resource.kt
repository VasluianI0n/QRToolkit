package com.divergentapp.qrtoolkit.core.common

sealed interface Resource<out T> {

    data class Success<T>(
        val data: T
    ) : Resource<T>

    data class Error(
        val type: ErrorType = ErrorType.UNKNOWN,
        val message: String? = null
    ) : Resource<Nothing>

    data object Loading : Resource<Nothing>
}