package com.example.leedstrinity.app.ui.home.itinerary

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itineraries")
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val notes: String
)
