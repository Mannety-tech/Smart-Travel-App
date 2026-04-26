package com.example.leedstrinity.app.ui.home.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leedstrinity.app.ui.home.navigation.NavRoutes

@Composable
fun SearchScreen(navController: NavController) {

    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<String>()) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Search Trips",
                style = MaterialTheme.typography.headlineMedium
            )

            // Search input
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Where do you want to go?") },
                modifier = Modifier.fillMaxWidth()
            )

            // Search button
            Button(
                onClick = {
                    // Placeholder search logic
                    results = if (query.isNotBlank()) {
                        listOf(
                            "$query City Tour",
                            "$query Museum Visit",
                            "$query Food Experience",
                            "$query Nightlife Guide"
                        )
                    } else emptyList()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            // Results list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(results) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            // Example: navigate to itinerary or details
                            navController.navigate(NavRoutes.Itinerary.route)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = item, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Tap to view details",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}



