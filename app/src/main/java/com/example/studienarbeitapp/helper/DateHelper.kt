package com.example.studienarbeitapp.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateHelper {
    companion object {
        @JvmStatic // This annotation makes the method accessible from Java as a static method
        fun formatDate(date: Date): String {
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            return format.format(date)
        }
    }
}