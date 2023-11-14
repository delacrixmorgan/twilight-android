package com.delacrixmorgan.twilight.android.timezone

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TimezoneScreen(
    modifier: Modifier,
    context: Context = LocalContext.current,
    viewModel: TimezoneViewModel = TimezoneViewModel()
) {
    Column(modifier) {
        Text(text = "Timezone")
        viewModel.m1()
    }
}

