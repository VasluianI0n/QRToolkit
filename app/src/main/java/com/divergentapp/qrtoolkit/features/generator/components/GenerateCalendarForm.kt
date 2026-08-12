package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.divergentapp.qrtoolkit.core.ui.icons.calendar_month
import com.divergentapp.qrtoolkit.core.ui.icons.chevron_right
import com.divergentapp.qrtoolkit.domain.model.QRContent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

enum class PickerType {
    START,
    END
}

@Composable
fun GenerateCalendarForm(
    content: QRContent.Calendar,
    onContentChanged: (QRContent) -> Unit
) {

    var pickerType by remember {
        mutableStateOf<PickerType?>(null)
    }

    val startHourMinute = remember(content.startTime) {
        parseTime(content.startTime)
    }

    val endHourMinute = remember(content.endTime) {
        parseTime(content.endTime)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = content.title,
            onValueChange = {
                onContentChanged(
                    content.copy(title = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Title")
            },
            singleLine = true,
            colors = defaultGeneratorTextFieldColors()
        )

        OutlinedTextField(
            value = content.location,
            onValueChange = {
                onContentChanged(
                    content.copy(location = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Location")
            },
            singleLine = true,
            colors = defaultGeneratorTextFieldColors()
        )

        OutlinedTextField(
            value = content.description,
            onValueChange = {
                onContentChanged(
                    content.copy(description = it)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Description")
            },
            minLines = 3,
            maxLines = 6,
            colors = defaultGeneratorTextFieldColors()
        )

        EventDateTimeCard(
            title = "Start",
            value = formatDateTime(
                content.startDate,
                content.startTime
            ),
            onClick = {
                pickerType = PickerType.START
            }
        )

        EventDateTimeCard(
            title = "End",
            value = formatDateTime(
                content.endDate,
                content.endTime
            ),
            onClick = {
                pickerType = PickerType.END
            }
        )

    }

    when (pickerType) {
        PickerType.START -> {
            DateTimePickerBottomSheet(
                initialDateMillis =
                    parseDateToMillis(content.startDate),
                minDateMillis =
                    todayMillis(),
                initialHour =
                    startHourMinute.first,
                initialMinute =
                    startHourMinute.second,
                onDismiss = {
                    pickerType = null
                },
                onConfirm = { date, time ->
                    onContentChanged(
                        content.copy(
                            startDate = date,
                            startTime = time,
                            endDate =
                                if (
                                    parseDateToMillis(content.endDate) <
                                    parseDateToMillis(date)
                                ) {
                                    date
                                } else {
                                    content.endDate
                                }
                        )
                    )
                    pickerType = null
                }
            )
        }

        PickerType.END -> {
            DateTimePickerBottomSheet(
                initialDateMillis =
                    parseDateToMillis(content.endDate),
                minDateMillis =
                    parseDateToMillis(content.startDate),
                initialHour =
                    endHourMinute.first,
                initialMinute =
                    endHourMinute.second,
                onDismiss = {
                    pickerType = null
                },
                onConfirm = { date, time ->
                    onContentChanged(
                        content.copy(
                            endDate = date,
                            endTime = time
                        )
                    )
                    pickerType = null
                }
            )
        }

        null -> Unit
    }
}

@Composable
fun defaultGeneratorTextFieldColors(): TextFieldColors {

    return OutlinedTextFieldDefaults.colors(

        focusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,
        unfocusedTextColor = MaterialTheme.colorScheme.tertiaryFixed,

        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,

        cursorColor = MaterialTheme.colorScheme.primary,

        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,

        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
    )

}

@Composable
fun EventDateTimeCard(
    title: String,
    value: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = calendar_month,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiaryFixed
                )

                Text(
                    value,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiaryFixed
                )

            }

            Icon(
                imageVector = chevron_right,
                contentDescription = null
            )

        }

    }

}

private fun formatDateTime(
    date: String,
    time: String
): String {

    if (date.isBlank() || time.isBlank()) {
        return "Select date & time"
    }

    return try {

        val parser =
            SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
            )

        val formatter =
            SimpleDateFormat(
                "EEEE, MMM d • HH:mm",
                Locale.getDefault()
            )

        formatter.format(
            parser.parse("$date $time")!!
        )

    } catch (_: Exception) {
        "$date • $time"
    }

}

private fun todayMillis(): Long {
    val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    utcCalendar.set(
        utcCalendar.get(Calendar.YEAR),
        utcCalendar.get(Calendar.MONTH),
        utcCalendar.get(Calendar.DAY_OF_MONTH),
        0,
        0,
        0
    )

    utcCalendar.set(Calendar.MILLISECOND, 0)

    return utcCalendar.timeInMillis
}

private fun parseDateToMillis(date: String): Long {

    if (date.isBlank()) {
        return todayMillis()
    }

    return try {

        val formatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        formatter.parse(date)?.time ?: todayMillis()

    } catch (_: Exception) {
        todayMillis()
    }

}

private fun parseTime(
    time: String
): Pair<Int, Int> {

    if (time.isBlank())
        return Calendar.getInstance().let {
            it.get(Calendar.HOUR_OF_DAY) to
                    it.get(Calendar.MINUTE)
        }

    val split = time.split(":")

    return split[0].toInt() to split[1].toInt()

}