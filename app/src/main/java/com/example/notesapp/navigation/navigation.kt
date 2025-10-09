package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.screens.SplashScreen

object Routes {
    const val splash = "splash"
}

@Composable
fun NavHost( nav: NavHostController = rememberNavController() ) {
    NavHost( nav, Routes.splash ) {
        composable( Routes.splash ) { backStackEntry ->
            SplashScreen( nav )
        }
    }
}