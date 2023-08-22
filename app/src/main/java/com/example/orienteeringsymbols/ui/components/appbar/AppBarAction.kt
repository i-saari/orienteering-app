package com.example.orienteeringsymbols.ui.components.appbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppBarAction(
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val onClick: () -> Unit
)