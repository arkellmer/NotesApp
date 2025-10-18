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

/**
 * Spacer used to ensure notifications bar
 * is not drawn under.
 */
@Composable
fun NotifSpacer() {
    Spacer(
        Modifier
            .windowInsetsTopHeight(WindowInsets.systemBars)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )
}

/**
 * Spacer used to ensure user controls (nav bar, keyboard, etc.)
 * are not drawn under.
 */
@Composable
fun CtrlSpacer() {
    Spacer(
        Modifier
            .windowInsetsBottomHeight(WindowInsets.systemBars)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    )
}

/**
 * HorizontalDivider used to emphasize different segments
 * of the screen.
 */
@Composable
fun ContentDivider() {
    HorizontalDivider(
        Modifier
            .height(1.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary
    )
}