package com.divergentapp.qrtoolkit.core.mvi

abstract class BaseScreenState(
    open val isLoading: Boolean = false,
    open val error: String? = null
) : UiState