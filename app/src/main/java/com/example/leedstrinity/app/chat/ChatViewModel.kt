package com.example.leedstrinity.app.chat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.leedstrinity.app.TFLiteIntentClassifier
import com.example.leedstrinity.app.flight.FlightRepository
import com.example.leedstrinity.app.ui.home.weather.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    application: Application,
    private val weatherRepository: WeatherRepository,
    private val flightRepository: FlightRepository
) : AndroidViewModel(application) {

    private val classifier: TFLiteIntentClassifier by lazy {
        TFLiteIntentClassifier(getApplication())
    }

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        addUserMessage(text)

        viewModelScope.launch {
            val reply = handleBotReply(text)
            addBotMessage(reply)
        }
    }

    private fun addUserMessage(text: String) {
        _messages.value = _messages.value + ChatMessage(
            text = text,
            isUser = true,
            timestamp = System.currentTimeMillis()
        )
    }

    private fun addBotMessage(text: String) {
        _messages.value = _messages.value + ChatMessage(
            text = text,
            isUser = false,
            timestamp = System.currentTimeMillis()
        )
    }

    // ---------------------------------------------------------
    // MAIN BOT ROUTER
    // ---------------------------------------------------------
    private suspend fun handleBotReply(message: String): String {
        val text = message.lowercase()

        // 1. Flight number detection
        extractFlightNumber(text)?.let { flightNum ->
            return getFlightStatusMessage(flightNum)
        }

        // 2. Weather detection
        if (isWeatherQuestion(text)) {
            val city = extractCity(text) ?: "London"
            return getRealWeather(city)
        }

        // 3. ML INTENT PREDICTION
        val predictedIntent = classifier.predict(text)
        android.util.Log.d("ML_TEST", "Predicted intent: $predictedIntent")

        when (predictedIntent) {

            "weather_city", "weather_today", "weather_forecast" -> {
                val city = extractCity(text) ?: "London"
                return getRealWeather(city)
            }

            "flight_status", "flight_delay", "flight_cancellation" -> {
                extractFlightNumber(text)?.let { return getFlightStatusMessage(it) }
                return "Tell me your flight number and I’ll check it."
            }

            "travel_tips" -> return "Here are some travel tips: stay hydrated, keep documents safe, and plan ahead."
            "visa_requirements" -> return "Visa requirements vary by country. Tell me your destination."
            "currency_info" -> return "Exchange rates change daily. Tell me the country and I’ll help."
            "safety_advice" -> return "Always stay aware of your surroundings and follow local guidance."
            "greeting" -> return "Hello! How can I help with your travel today?"
            "goodbye" -> return "Safe travels! Let me know if you need anything else."
            "thanks" -> return "You're welcome!"
            "smalltalk" -> return "I'm here to help with travel, weather, and flights."
            "lost_passport" -> return "If you lost your passport, contact your embassy immediately."
            "medical_help" -> return "If this is an emergency, call local emergency services."
            "emergency_numbers" -> return "Emergency numbers vary by country. Tell me where you are."

            else -> {
                // Unknown ML intent
                return "I'm still learning. Could you rephrase that?"
            }
        }

        // 4. FAQ fallback
        getFaqResponse(text)?.let { return it }

        // ---------------------------------------------------------
        // 5. FINAL SAFE FALLBACK (THIS FIXES YOUR CRASH)
        // ---------------------------------------------------------
        return "I'm not sure yet, but I can help with weather, flights, and travel tips."
    }

    // ---------------------------------------------------------
    // WEATHER HANDLING
    // ---------------------------------------------------------
    private fun isWeatherQuestion(input: String): Boolean {
        val t = input.lowercase()
        return t.contains("weather") ||
                t.contains("temperature") ||
                t.contains("hot") ||
                t.contains("cold") ||
                t.contains("rain") ||
                t.contains("forecast")
    }

    private suspend fun getRealWeather(city: String): String {
        return try {
            val weather = weatherRepository.getWeather(city)
            val temp = weather.main.temp
            val desc = weather.weather.firstOrNull()?.description ?: "unknown"
            "Weather in $city: $desc, $temp°C"
        } catch (e: Exception) {
            "I couldn’t fetch the weather for $city right now."
        }
    }

    private fun extractCity(input: String): String? {
        val words = input.lowercase().split(" ")
        val idx = words.indexOf("in")
        return if (idx != -1 && idx + 1 < words.size) {
            words[idx + 1].replaceFirstChar { it.uppercase() }
        } else null
    }

    // ---------------------------------------------------------
    // FLIGHT HANDLING
    // ---------------------------------------------------------
    private fun extractFlightNumber(text: String): String? {
        val regex = Regex("[A-Za-z]{2}\\d{1,4}")
        return regex.find(text)?.value
    }

    private suspend fun getFlightStatusMessage(flightNumber: String): String {
        return try {
            val response = flightRepository.getFlightStatus(flightNumber)
                ?: return "I couldn’t find any data for flight $flightNumber."

            val flight = response.data.firstOrNull()
                ?: return "I couldn’t find any data for flight $flightNumber."

            val delay = flight.departure.delay ?: 0
            val airline = flight.airline.name ?: "Unknown airline"
            val depAirport = flight.departure.airport ?: "Unknown airport"
            val arrAirport = flight.arrival.airport ?: "Unknown airport"

            if (delay > 0) {
                "Flight $flightNumber ($airline) from $depAirport to $arrAirport is delayed by $delay minutes."
            } else {
                "Flight $flightNumber ($airline) from $depAirport to $arrAirport is currently on time."
            }

        } catch (e: Exception) {
            "I couldn’t fetch the status for $flightNumber."
        }
    }

    // ---------------------------------------------------------
    // FAQ FALLBACK
    // ---------------------------------------------------------
    private fun getFaqResponse(input: String): String? {
        val text = input.lowercase()

        return when {
            text.contains("weather") && text.contains("today") ->
                "You can expect mild conditions today. For exact details, check the Weather section."

            text.contains("rain") ->
                "Rain is possible depending on your location. Always good to pack an umbrella."

            text.contains("temperature") ->
                "Temperatures vary by region. Tell me your destination and I can help more."

            text.contains("flight") && text.contains("delay") ->
                "Flight delays are common during peak travel times. Keep an eye on your airline’s app."

            text.contains("cancelled") && text.contains("flight") ->
                "If your flight is cancelled, airlines usually offer rebooking or refunds."

            text.contains("boarding") ->
                "Most flights begin boarding 30–50 minutes before departure."

            text.contains("passport") ->
                "Make sure your passport is valid for at least 6 months beyond your travel date."

            text.contains("luggage") || text.contains("baggage") ->
                "Most airlines allow 1 carry‑on and 1 personal item. Check your airline for exact limits."

            else -> null
        }
    }
}















