package com.example.studienarbeitapp.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A utility object for handling date formatting.
 */
object DateHelper {
    /**
     * Formats a given date into a string with the pattern "dd-MM-yyyy HH:mm:ss".
     * The pattern includes day, month, year, hour, minute, and second, using the default locale.
     *
     * @param date The date to be formatted.
     * @return A string representation of the date in the format "dd-MM-yyyy HH:mm:ss".
     */
    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
