package com.example.gymguru.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val xs: Dp = 2.dp,
    val s: Dp = 4.dp,
    val m: Dp = 8.dp,
    val l: Dp = 12.dp,
    val xl: Dp = 16.dp,
    val imageItem: Dp = 132.dp,
    val recentlyPlayedImage: Dp = 64.dp,
    val recentlyPlayedIcon: Dp = 24.dp,
    val loginIcon: Dp = 96.dp,
    val loginRoundedCorner: Dp = 32.dp,
    val iconMedium: Dp = 32.dp,
    val cardMedium: Dp = 164.dp
)

val LocalDimens = compositionLocalOf { Dimens() }
val MaterialTheme.dimensions: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current
