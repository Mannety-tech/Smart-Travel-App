package com.example.leedstrinity.app.ui.home.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    navController: NavController,
    viewModel: ItineraryViewModel = viewModel()
) {
    val itineraries by viewModel.itineraries.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Your Itineraries") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createItinerary") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Itinerary")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (itineraries.isEmpty()) {
                Text(
                    text = "No itineraries yet. Tap + to create one.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(itineraries) { item ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                navController.navigate("itineraryDetails/${item.id}")
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "${item.startDate} → ${item.endDate}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = item.destination,
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
}




