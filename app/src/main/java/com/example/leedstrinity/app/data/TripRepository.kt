package com.example.leedstrinity.app.data

class TripRepository(private val dao: TripDao) {

    val trips = dao.getTrips()

    suspend fun addTrip(trip: Trip) {
        dao.addTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        dao.deleteTrip(trip)
    }
}
