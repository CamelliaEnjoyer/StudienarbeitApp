package com.example.studienarbeitapp.helper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateHelperTest {

    @Test
    fun testFormatDate() {
        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 45)
        }.time

        val expectedFormat = "15-06-2023 10:30:45"
        val formattedDate = DateHelper.formatDate(date)
        assertEquals(expectedFormat, formattedDate)
    }

    @Test
    fun testFormatDateWithDifferentLocale() {
        val originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.FRANCE)

        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 45)
        }.time

        val expectedFormat = "15-06-2023 10:30:45"
        val formattedDate = DateHelper.formatDate(date)
        assertEquals(expectedFormat, formattedDate)

        Locale.setDefault(originalLocale)  // Reset the locale to original
    }

    @Test
    fun testFormatDateWithDifferentTimeZone() {
        val originalTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"))

        val date = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.DAY_OF_MONTH, 15)
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 45)
        }.time

        val expectedFormat = "15-06-2023 10:30:45"
        val formattedDate = DateHelper.formatDate(date)
        assertEquals(expectedFormat, formattedDate)

        TimeZone.setDefault(originalTimeZone)  // Reset the time zone to original
    }

    @Test
    fun testFormatDateWithNullDate() {
        val date: Date? = null
        try {
            DateHelper.formatDate(date!!)
        } catch (e: NullPointerException) {
            assertEquals(NullPointerException::class.java, e::class.java)
        }
    }
}
