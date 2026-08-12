package com.divergentapp.qrtoolkit.core.mvi

import com.divergentapp.qrtoolkit.core.common.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        Intent : UiIntent,
        State : UiState,
        Effect : UiEffect
        >(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected val currentState: State
        get() = _state.value

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    /**
     * Entry point from the UI.
     */
    fun dispatch(intent: Intent) {
        onIntent(intent)
    }

    /**
     * Handle user intents.
     */
    protected abstract fun onIntent(intent: Intent)

    /**
     * Update immutable state.
     */
    protected fun setState(
        reducer: State.() -> State
    ) {
        _state.value = currentState.reducer()
    }

    /**
     * Send one-time UI events.
     */
    protected fun sendEffect(
        effect: Effect
    ) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    /**
     * Override in child ViewModels if they want
     * automatic loading handling.
     */
    protected open fun onLoading(
        loading: Boolean
    ) = Unit

    /**
     * Override in child ViewModels if they want
     * automatic error handling.
     */
    protected open fun onError(
        message: String?
    ) = Unit

    /**
     * Launch coroutine with optional loading.
     */
    protected fun launch(
        showLoading: Boolean = false,
        onException: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit
    ) {

        viewModelScope.launch {

            try {

                if (showLoading) {
                    onLoading(true)
                }

                block()

            } catch (throwable: Throwable) {

                onError(throwable.message)
                onException?.invoke(throwable)

            } finally {

                if (showLoading) {
                    onLoading(false)
                }

            }

        }

    }

    /**
     * Collect any Flow.
     */
    protected fun <T> collect(
        flow: Flow<T>,
        collector: suspend (T) -> Unit
    ) {

        viewModelScope.launch {

            flow.collectLatest {
                collector(it)
            }

        }

    }

    /**
     * Collect a Flow<Resource<T>>.
     */
    protected fun <T> collectResource(
        flow: Flow<Resource<T>>,
        onSuccess: suspend (T) -> Unit
    ) {

        collect(flow) { resource ->

            when (resource) {

                Resource.Loading -> {
                    onLoading(true)
                }

                is Resource.Success -> {

                    onLoading(false)
                    onSuccess(resource.data)

                }

                is Resource.Error -> {

                    onLoading(false)
                    onError(resource.message)

                }

            }

        }

    }

    /**
     * Handle Resource<T> from suspend functions.
     */
    protected suspend fun <T> handleResource(
        resource: Resource<T>,
        onSuccess: suspend (T) -> Unit
    ) {

        when (resource) {

            Resource.Loading -> {
                onLoading(true)
            }

            is Resource.Success -> {

                onLoading(false)
                onSuccess(resource.data)

            }

            is Resource.Error -> {

                onLoading(false)
                onError(resource.message)

            }

        }

    }

}