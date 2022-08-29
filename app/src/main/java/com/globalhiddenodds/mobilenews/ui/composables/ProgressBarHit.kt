package com.globalhiddenodds.mobilenews.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ProgressBarHit(isDisplayed: Boolean) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = colors.secondary
            )
        }
    }
}

