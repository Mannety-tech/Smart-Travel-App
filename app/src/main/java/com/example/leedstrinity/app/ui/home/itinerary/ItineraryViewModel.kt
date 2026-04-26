package com.example.leedstrinity.app.ui.home.itinerary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.leedstrinity.app.ui.home.itinerary.ItineraryDatabase
import com.example.leedstrinity.app.ui.home.itinerary.ItineraryEntity
import com.example.leedstrinity.app.ui.home.itinerary.ItineraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItineraryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ItineraryDatabase.getDatabase(application).itineraryDao()
    private val repo = ItineraryRepository(dao)

    private val _itineraries = MutableStateFlow<List<ItineraryEntity>>(emptyList())
    val itineraries: StateFlow<List<ItineraryEntity>> = _itineraries

    init {
        loadItineraries()
    }

    fun loadItineraries() {
        viewModelScope.launch {
            _itineraries.value = repo.getAll()
        }
    }

    fun addItinerary(title: String, destination: String, start: String, end: String, notes: String) {
        viewModelScope.launch {
            repo.insert(
                ItineraryEntity(
                    title = title,
                    destination = destination,
                    startDate = start,
                    endDate = end,
                    notes = notes
                )
            )
            loadItineraries()
        }
    }

    fun updateItinerary(id: Int, title: String, destination: String, start: String, end: String, notes: String) {
        viewModelScope.launch {
            repo.update(
                ItineraryEntity(
                    id = id,
                    title = title,
                    destination = destination,
                    startDate = start,
                    endDate = end,
                    notes = notes
                )
            )
            loadItineraries()
        }
    }


    fun deleteItinerary(itinerary: ItineraryEntity) {
        viewModelScope.launch {
            repo.delete(itinerary)
            loadItineraries()
        }
    }
}


