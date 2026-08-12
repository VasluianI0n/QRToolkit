package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.divergentapp.qrtoolkit.domain.model.QRContent

@Composable
fun GenerateContentForm(
    type: GenerateType,
    content: QRContent,
    isUploading: Boolean,
    uploadProgress: Int,
    onContentChanged: (QRContent) -> Unit,
    onFilePick: () -> Unit
) {

    when (type) {

        GenerateType.TEXT -> {
            GenerateTextForm(
                content = content as QRContent.Text,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.PDF -> {
            GeneratePdfForm(
                fileName = (content as QRContent.UploadedFile).fileName,
                isUploading = isUploading,
                onPickPdf = onFilePick,
                uploadProgress = uploadProgress
            )
        }

        GenerateType.IMAGE -> {
            GenerateImageForm(
                fileName = (content as QRContent.UploadedFile).fileName,
                isUploading = isUploading,
                uploadProgress = uploadProgress,
                onPickImage = onFilePick
            )
        }

        GenerateType.VIDEO -> {
            GenerateVideoForm(
                fileName = (content as QRContent.UploadedFile).fileName,
                isUploading = isUploading,
                uploadProgress = uploadProgress,
                onPickVideo = onFilePick
            )
        }

        GenerateType.AUDIO -> {
            GenerateAudioForm(
                fileName = (content as QRContent.UploadedFile).fileName,
                isUploading = isUploading,
                uploadProgress = uploadProgress,
                onPickAudio = onFilePick
            )
        }


        GenerateType.URL -> {
            GenerateUrlForm(
                content = content as QRContent.Url,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.PHONE -> {
            GeneratePhoneForm(
                content = content as QRContent.Phone,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.SMS -> {
            GenerateSmsForm(
                content = content as QRContent.Sms,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.EMAIL -> {
            GenerateEmailForm(
                content = content as QRContent.Email,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.WIFI -> {
            GenerateWifiForm(
                content = content as QRContent.Wifi,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.LOCATION -> {
            GenerateLocationForm(
                content = content as QRContent.Location,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.CONTACT -> {
            GenerateContactForm(
                content = content as QRContent.Contact,
                onContentChanged = onContentChanged
            )
        }

        GenerateType.CALENDAR -> {
            GenerateCalendarForm(
                content = content as QRContent.Calendar,
                onContentChanged = onContentChanged
            )
        }

    }

}