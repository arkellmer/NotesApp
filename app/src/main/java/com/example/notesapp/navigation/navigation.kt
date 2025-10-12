package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.screens.HomeScreen
import com.example.notesapp.screens.SplashScreen
import com.example.notesapp.viewmodel.NoteViewModel

object Routes {
    const val Splash = "splash"
    const val Home = "home"
}

@Composable
fun NavHost(nav: NavHostController = rememberNavController(), vm: NoteViewModel) {
    NavHost(nav, Routes.Splash) {
        composable(Routes.Splash) {
            SplashScreen(nav)
        }

        composable(Routes.Home) {
            HomeScreen(nav, vm)
        }
    }
}