package com.divergentapp.qrtoolkit.core.ui.extensions

import com.divergentapp.qrtoolkit.domain.model.QRHistory
import com.divergentapp.qrtoolkit.features.history.state.HistorySection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


private val sectionDateFormatter by lazy {
    SimpleDateFormat(
        "dd MMM yyyy",
        Locale.getDefault()
    )
}

fun List<QRHistory>.toHistorySections(): List<HistorySection> {

    return groupBy { history ->
        history.createdAt.toHistorySectionTitle()
    }.map { (title, items) ->

        HistorySection(
            title = title,
            items = items
        )

    }

}

fun Long.toHistorySectionTitle(): String {

    val itemCalendar = Calendar.getInstance().apply {
        timeInMillis = this@toHistorySectionTitle
    }

    val today = Calendar.getInstance()

    if (itemCalendar.isSameDay(today)) {
        return "Today"
    }

    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }

    if (itemCalendar.isSameDay(yesterday)) {
        return "Yesterday"
    }

    return sectionDateFormatter.format(Date(this))

}

private fun Calendar.isSameDay(other: Calendar): Boolean {

    return get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
            get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)

}