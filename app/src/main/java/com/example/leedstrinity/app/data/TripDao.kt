package com.example.leedstrinity.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * FROM Trip ORDER BY id DESC")
    fun getTrips(): Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)
}
