package com.delacrixmorgan.twilight.android

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppScaffold(
    gesturesPadding: Boolean = true,
    bottomBar: @Composable () -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(bottomBar = bottomBar) {
        val insetModifier = if (gesturesPadding) {
            Modifier
                .fillMaxSize()
                .padding(it)
                .consumeWindowInsets(it)
                .systemGesturesPadding()
        } else {
            Modifier
                .padding(it)
                .consumeWindowInsets(it)
                .systemBarsPadding()
        }
        content(insetModifier)
    }
}