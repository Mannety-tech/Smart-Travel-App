package com.example.leedstrinity.app.ui.home.itinerary


class ItineraryRepository(private val dao: ItineraryDao) {

    suspend fun getAll() = dao.getAllItineraries()

    suspend fun getById(id: Int) = dao.getItineraryById(id)

    suspend fun insert(itinerary: ItineraryEntity) = dao.insertItinerary(itinerary)

    suspend fun update(itinerary: ItineraryEntity) = dao.updateItinerary(itinerary)

    suspend fun delete(itinerary: ItineraryEntity) = dao.deleteItinerary(itinerary)


}
