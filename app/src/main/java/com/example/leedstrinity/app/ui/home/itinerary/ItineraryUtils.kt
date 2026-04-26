package com.example.leedstrinity.app.ui.home.itinerary

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun parseDate(date: String): Long? {
    return try {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        formatter.parse(date)?.time
    } catch (e: Exception) {
        null
    }
}

fun showToast(context: android.content.Context, message: String) {
    android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show()
}
