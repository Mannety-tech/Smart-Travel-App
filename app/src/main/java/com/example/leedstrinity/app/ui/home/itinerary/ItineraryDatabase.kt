package com.example.leedstrinity.app.ui.home.itinerary


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ItineraryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ItineraryDatabase : RoomDatabase() {

    abstract fun itineraryDao(): ItineraryDao

    companion object {
        @Volatile private var INSTANCE: ItineraryDatabase? = null

        fun getDatabase(context: Context): ItineraryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ItineraryDatabase::class.java,
                    "itinerary_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
