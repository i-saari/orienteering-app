package com.knollsoftware.orienteeringsymbols.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SpecialInstruction(
    @DrawableRes val imageResourceId: Int,
    @StringRes val body: Int,
    @StringRes val description: Int
)