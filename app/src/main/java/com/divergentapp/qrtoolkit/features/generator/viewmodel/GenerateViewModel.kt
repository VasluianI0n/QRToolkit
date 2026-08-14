package com.divergentapp.qrtoolkit.features.generator.viewmodel

import androidx.lifecycle.viewModelScope
import com.divergentapp.qrtoolkit.core.common.Resource
import com.divergentapp.qrtoolkit.core.mvi.BaseViewModel
import com.divergentapp.qrtoolkit.core.qr.QRContentEncoder
import com.divergentapp.qrtoolkit.domain.model.QRContent
import com.divergentapp.qrtoolkit.domain.model.QRFormat
import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.domain.model.Settings
import com.divergentapp.qrtoolkit.domain.usecase.GenerateQRUseCase
import com.divergentapp.qrtoolkit.domain.usecase.GetSettingsUseCase
import com.divergentapp.qrtoolkit.domain.usecase.SaveHistoryUseCase
import com.divergentapp.qrtoolkit.domain.usecase.UploadFileUseCase
import com.divergentapp.qrtoolkit.features.generator.model.GenerateEffect
import com.divergentapp.qrtoolkit.features.generator.model.GenerateIntent
import com.divergentapp.qrtoolkit.features.generator.model.GenerateState
import com.divergentapp.qrtoolkit.features.generator.model.defaultContent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.io.File

class GenerateViewModel(
    private val generateQR: GenerateQRUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase,
    private val qrContentEncoder: QRContentEncoder,
    getSettingsUseCase: GetSettingsUseCase
) : BaseViewModel<
        GenerateIntent,
        GenerateState,
        GenerateEffect
        >(
    GenerateState()
) {

    val settings = getSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    override fun onIntent(intent: GenerateIntent) {
        when (intent) {
            is GenerateIntent.SelectType -> {
                setState {
                    copy(
                        selectedType = intent.type,
                        content = intent.type.defaultContent(),
                        bitmap = null
                    )
                }
            }

            is GenerateIntent.UpdateContent -> {
                setState {
                    copy(
                        content = intent.content,
                        bitmap = null
                    )
                }
            }

            GenerateIntent.Generate -> generate()
            is GenerateIntent.FileSelected -> {
                launch {
                    uploadFile(
                        intent.file,
                        intent.mimeType
                    )
                }
            }

            GenerateIntent.DismissPreview -> {
                setState {
                    copy(
                        bitmap = null
                    )
                }
            }
        }
    }

    private suspend fun uploadFile(
        file: File,
        mimeType: String
    ) {
        setState {
            copy(
                isUploading = true,
                error = null
            )
        }

        when (
            val upload = uploadFileUseCase(file, mimeType) { progress ->
                setState {
                    copy(
                        uploadProgress = progress
                    )
                }
            }
        ) {
            is Resource.Success -> {
                val uploadedFile = QRContent.UploadedFile(
                    fileName = upload.data.fileName,
                    url = upload.data.url
                )

                val bitmap = generateQR(uploadedFile)

                setState {
                    copy(
                        isUploading = false,
                        uploadProgress = 100,
                        content = uploadedFile,
                        bitmap = bitmap
                    )
                }

                sendEffect(
                    GenerateEffect.ShowMessage("The Link is available for 24 hours")
                )
            }

            is Resource.Error -> {
                setState {
                    copy(
                        isUploading = false,
                        error = upload.message,
                        uploadProgress = 0,
                    )
                }

                sendEffect(
                    GenerateEffect.ShowMessage("Check your internet connection & File Size")
                )
            }

            Resource.Loading -> Unit

        }

    }

    private fun generate() {
        launch {
            val bitmap = generateQR(currentState.content)

            if (settings.value.saveGeneratedHistory) {
                val rawValue = qrContentEncoder.encode(currentState.content)
                handleResource(
                    resource = saveHistoryUseCase(
                        QRHistory(
                            content = QRContent.UploadedFile(),
                            format = QRFormat.QR_CODE,
                            rawValue = rawValue,
                            createdAt = System.currentTimeMillis()
                        )
                    ),
                    onSuccess = {
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                )

            }
            setState {
                copy(
                    bitmap = bitmap
                )
            }

            sendEffect(
                GenerateEffect.ShowInterstitial
            )
        }
    }
}