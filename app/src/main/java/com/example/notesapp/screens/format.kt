package com.example.notesapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotifSpacer() {
    Spacer(
        Modifier
            .windowInsetsTopHeight(WindowInsets.systemBars)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )
}

@Composable
fun CtrlSpacer() {
    Spacer(
        Modifier
            .windowInsetsBottomHeight(WindowInsets.systemBars)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )
}

@Composable
fun ContentDivider() {
    HorizontalDivider(
        Modifier
            .height(1.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary
    )
}