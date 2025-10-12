package com.example.notesapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.notesapp.navigation.Routes
import kotlinx.coroutines.delay
import com.example.notesapp.R

@Composable
fun SplashScreen(nav: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1000)
        nav.navigate(Routes.Home) {
            popUpTo(Routes.Splash) {
                inclusive = true
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Notes app logo."
        )
    }
}