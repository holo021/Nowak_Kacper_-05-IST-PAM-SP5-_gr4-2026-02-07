package com.example.photomapproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.photomapproject.ui.theme.PhotoMapProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoMapProjectTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    // Ekran 1: Lista (Home)
                    composable("home") {
                        HomeScreen(
                            onPhotoClick = { photoId ->
                                navController.navigate("details/$photoId")
                            }
                        )
                    }

                    // Ekran 2: Szczegóły (Details) - TO SPEŁNIA WYMÓG 2 EKRANÓW
                    composable(
                        route = "details/{photoId}",
                        arguments = listOf(navArgument("photoId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val photoId = backStackEntry.arguments?.getLong("photoId") ?: 0L
                        DetailsScreen(
                            photoId = photoId,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}