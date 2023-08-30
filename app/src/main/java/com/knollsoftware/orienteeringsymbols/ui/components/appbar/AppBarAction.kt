package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Class representing a top app bar action button
 */
data class AppBarAction(
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val onClick: () -> Unit
)