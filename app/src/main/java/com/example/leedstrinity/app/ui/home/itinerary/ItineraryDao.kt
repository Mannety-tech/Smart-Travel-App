package com.example.leedstrinity.app.ui.home.itinerary


import androidx.room.*

@Dao
interface ItineraryDao {

    @Query("SELECT * FROM itineraries ORDER BY id DESC")
    suspend fun getAllItineraries(): List<ItineraryEntity>

    @Query("SELECT * FROM itineraries WHERE id = :id")
    suspend fun getItineraryById(id: Int): ItineraryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itinerary: ItineraryEntity)

    @Update
    suspend fun updateItinerary(itinerary: ItineraryEntity)

    @Delete
    suspend fun deleteItinerary(itinerary: ItineraryEntity)
}
