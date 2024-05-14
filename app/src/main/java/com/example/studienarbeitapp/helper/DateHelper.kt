package com.example.studienarbeitapp.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper{
    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
