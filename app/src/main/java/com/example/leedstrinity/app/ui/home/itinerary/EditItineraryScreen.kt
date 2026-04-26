package com.example.leedstrinity.app.ui.home.itinerary

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItineraryScreen(
    navController: NavController,
    tripId: String,
    viewModel: ItineraryViewModel = viewModel()
) {
    val context = LocalContext.current

    // Load itinerary from Room
    val itinerary = viewModel.itineraries.collectAsState().value
        .firstOrNull { it.id.toString() == tripId }

    if (itinerary == null) {
        Text("Loading...")
        return
    }

    // Pre-filled fields
    var tripTitle by remember { mutableStateOf(itinerary.title) }
    var destination by remember { mutableStateOf(itinerary.destination) }
    var notes by remember { mutableStateOf(itinerary.notes) }

    // Date range picker state
    var showRangePicker by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(itinerary.startDate) }
    var endDate by remember { mutableStateOf(itinerary.endDate) }

    var startMillis by remember { mutableStateOf(parseDate(itinerary.startDate)) }
    var endMillis by remember { mutableStateOf(parseDate(itinerary.endDate)) }

    // Date Range Picker Dialog
    if (showRangePicker) {
        val rangeState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = startMillis,
            initialSelectedEndDateMillis = endMillis
        )

        DatePickerDialog(
            onDismissRequest = { showRangePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showRangePicker = false

                    rangeState.selectedStartDateMillis?.let {
                        startMillis = it
                        startDate = formatDate(it)
                    }

                    rangeState.selectedEndDateMillis?.let {
                        endMillis = it
                        endDate = formatDate(it)
                    }
                }) {
                    Text("OK")
                }
            }
        ) {
            DateRangePicker(state = rangeState)
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Edit Itinerary") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = tripTitle,
                onValueChange = { tripTitle = it },
                label = { Text("Trip Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination") },
                modifier = Modifier.fillMaxWidth()
            )

            // Date Range Picker Button
            OutlinedButton(
                onClick = { showRangePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Select Dates",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("$startDate → $endDate")
            }

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Save Button
            Button(
                onClick = {
                    // VALIDATION
                    if (tripTitle.isBlank() || destination.isBlank()) {
                        showToast(context, "Please fill in all required fields")
                        return@Button
                    }

                    if (startMillis == null || endMillis == null) {
                        showToast(context, "Please select trip dates")
                        return@Button
                    }

                    if (endMillis!! < startMillis!!) {
                        showToast(context, "End date must be after start date")
                        return@Button
                    }

                    // Save updates
                    viewModel.updateItinerary(
                        id = itinerary.id,
                        title = tripTitle,
                        destination = destination,
                        start = startDate,
                        end = endDate,
                        notes = notes
                    )

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}





