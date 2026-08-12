package com.divergentapp.qrtoolkit.features.generator.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerBottomSheet(
    initialDateMillis: Long,
    minDateMillis: Long,
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {

    var page by remember {
        mutableStateOf(Page.DATE)
    }

    val calendar = remember {
        Calendar.getInstance()
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(
                utcTimeMillis: Long
            ): Boolean {
                return utcTimeMillis >= minDateMillis
            }

        }
    )

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(0.95f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {

            Column (Modifier.padding(vertical = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedContent(
                    targetState = page,
                    transitionSpec = {
                        slideInHorizontally { it } +
                                fadeIn() togetherWith
                                slideOutHorizontally { -it } +
                                fadeOut()
                    },
                    label = ""
                ) { current ->
                    when (current) {

                        Page.DATE -> {

                            DatePicker(
                                modifier = Modifier.fillMaxWidth(),
                                state = datePickerState,
                                colors = DatePickerDefaults.colors().copy(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    weekdayContentColor = MaterialTheme.colorScheme.primary,
                                    yearContentColor = MaterialTheme.colorScheme.primary,
                                    currentYearContentColor = MaterialTheme.colorScheme.primary,
                                    selectedYearContentColor = MaterialTheme.colorScheme.background,
                                    selectedDayContentColor = MaterialTheme.colorScheme.background,
                                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                                    dateTextFieldColors = defaultGeneratorTextFieldColors(),
                                    dayContentColor = MaterialTheme.colorScheme.primary,
                                    disabledDayContentColor = MaterialTheme.colorScheme.outline,
                                    disabledYearContentColor = MaterialTheme.colorScheme.outline,
                                )
                            )

                        }

                        Page.TIME -> {

                            TimePicker(
                                modifier = Modifier.fillMaxWidth(),
                                state = timePickerState,
                                colors = TimePickerDefaults.colors().copy(
                                    clockDialColor = MaterialTheme.colorScheme.primary,
                                    clockDialSelectedContentColor = MaterialTheme.colorScheme.primary,
                                    clockDialUnselectedContentColor = MaterialTheme.colorScheme.background,
                                    selectorColor = MaterialTheme.colorScheme.background,
                                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primary,
                                    timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.primary,
                                    timeSelectorSelectedContentColor = MaterialTheme.colorScheme.background,
                                    timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.background
                                )
                            )

                        }

                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
                ) {

                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }

                    if (page == Page.DATE) {

                        Button(
                            onClick = {
                                page = Page.TIME
                            }
                        ) {
                            Text("Next")
                        }

                    } else {

                        Button(
                            onClick = {

                                val millis =
                                    datePickerState.selectedDateMillis
                                        ?: System.currentTimeMillis()

                                calendar.timeInMillis = millis

                                val date =
                                    SimpleDateFormat(
                                        "yyyy-MM-dd",
                                        Locale.getDefault()
                                    ).format(calendar.time)

                                val time =
                                    "%02d:%02d".format(
                                        timePickerState.hour,
                                        timePickerState.minute
                                    )

                                onConfirm(date, time)

                            }
                        ) {
                            Text("Done")
                        }

                    }

                }

            }

        }
    }

}

private enum class Page {
    DATE,
    TIME
}