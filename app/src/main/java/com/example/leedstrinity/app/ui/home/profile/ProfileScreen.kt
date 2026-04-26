package com.example.leedstrinity.app.ui.home.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.leedstrinity.app.ui.home.navigation.NavRoutes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium
            )

            // User info card
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Logged in as:",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = user?.email ?: "Unknown user",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Edit Profile (future feature)
            Button(
                onClick = {
                    // TODO: Navigate to Edit Profile screen
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Profile")
            }

            // View itineraries
            Button(
                onClick = {
                    navController.navigate(NavRoutes.Itinerary.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View My Itineraries")
            }

            // Logout
            Button(
                onClick = {
                    auth.signOut()
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("Log Out")
            }
        }
    }
}


