package com.example.leedstrinity.app.ui.home.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.leedstrinity.app.ui.home.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTestScreen(
    weatherViewModel: WeatherViewModel = viewModel()
) {
    var city by remember { mutableStateOf("") }
    val weather by weatherViewModel.weather.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Weather Test Page") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (city.isNotBlank()) {
                        errorMessage = null
                        weatherViewModel.loadWeather(city)
                    } else {
                        errorMessage = "City cannot be empty"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Check Weather")
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            weather?.let { w ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Weather in ${w.name}", style = MaterialTheme.typography.titleMedium)
                        Text("${w.main.temp}°C — ${w.weather[0].description}")
                        Text("Humidity: ${w.main.humidity}%")
                    }
                }
            }
        }
    }
}

