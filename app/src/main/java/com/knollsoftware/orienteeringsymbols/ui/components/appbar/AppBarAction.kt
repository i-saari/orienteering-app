package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Class representing a top app bar action button
 */
data class AppBarAction(
    val icon: ImageVector,
    val description: String,
    val onClick: () -> Unit
)