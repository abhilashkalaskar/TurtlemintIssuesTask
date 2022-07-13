package com.abhilash.githubissues.utils

import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun changeDateFormat(rawDate: String): String {
        val dateInstance = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(rawDate)
        val calendar = Calendar.getInstance()
        calendar.time = dateInstance

        val newDateFormat = SimpleDateFormat("MM-dd-yyyy")
        val newDate = newDateFormat.format(dateInstance)

        return newDate.toString()
    }


}