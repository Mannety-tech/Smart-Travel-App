package com.example.leedstrinity.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff

@Composable
fun HomeScreen(navController: NavController? = null) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FlightTakeoff,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Smart Travel App",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Plan smarter. Travel easier.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Start Planning Button
            Button(
                onClick = { navController?.navigate("search") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Planning")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController?.navigate("weatherTest") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Weather  Page")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController?.navigate("directions_test") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Directions  Page")
            }

            Button(onClick = { navController?.navigate("chat") }) {
                Text("Chatbox")
            }

            Button(onClick = { navController?.navigate("mapTest") }) {
                Text("Test Map")
            }





            Spacer(modifier = Modifier.height(32.dp))

            // Popular Destinations
            Text(
                text = "Popular Destinations",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TravelCard("Paris", "City of lights and romance")
                TravelCard("Tokyo", "Where tradition meets technology")
                TravelCard("New York", "The city that never sleeps")
            }
        }
    }
}

@Composable
fun TravelCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}







