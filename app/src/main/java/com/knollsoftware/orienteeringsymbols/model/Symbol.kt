package com.knollsoftware.orienteeringsymbols.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Class representing a symbol with image, name, group and description
 */

data class Symbol(
    val group: String,
    @DrawableRes val controlImageResourceId: Int,
    val name: String,
    val description: String
)