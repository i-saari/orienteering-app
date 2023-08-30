package com.knollsoftware.orienteeringsymbols.ui.components.appdrawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Class representing a navigation entry in the navigation drawer
 */
data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val descriptionId: Int
)