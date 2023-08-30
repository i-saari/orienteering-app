package com.knollsoftware.orienteeringsymbols.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Class representing a symbol with image, name, group and description
 */

data class Symbol(
    @StringRes val group: Int,
    @DrawableRes val controlImageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val description: Int
)