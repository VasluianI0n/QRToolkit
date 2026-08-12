package com.divergentapp.qrtoolkit.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

suspend inline fun <T> resourceOf(
    crossinline block: suspend () -> T
): Resource<T> {

    return try {
        Resource.Success(block())
    } catch (e: Exception) {
        Resource.Error(
            message = e.message
        )
    }
}

inline fun <T, R> Flow<T>.asResource(
    crossinline transform: (T) -> R
): Flow<Resource<R>> {

    return map<T, Resource<R>> { value ->
        Resource.Success(transform(value))
    }.catch { exception ->
        emit(
            Resource.Error(
                message = exception.message
            )
        )
    }
}