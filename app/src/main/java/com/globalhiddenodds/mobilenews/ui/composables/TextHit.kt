package com.globalhiddenodds.mobilenews.ui.composables

import android.text.Layout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Title(title: String){
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier
            .padding(10.dp)
            .wrapContentWidth(Alignment.Start)
    )
}

@Composable
fun Detail(description: String) {
    Text(
        text = description,
        style = MaterialTheme.typography.overline,
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 5.dp, bottom = 10.dp)
            .wrapContentWidth(Alignment.Start)
    )
}