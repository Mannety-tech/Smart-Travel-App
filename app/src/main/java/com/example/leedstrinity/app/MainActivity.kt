package com.example.leedstrinity.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.leedstrinity.app.ui.home.navigation.AppNavHost
import com.example.leedstrinity.smarttravelappclean.BuildConfig   //

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  This will now correctly print your API key
        Log.d("API_KEY_TEST", "Key = ${BuildConfig.MAPS_API_KEY}")

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}


