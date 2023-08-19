package com.example.orienteeringsymbols.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Symbol(
    @StringRes val group: Int,
    @DrawableRes val controlImageResourceId: Int,
    @DrawableRes val mapImageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val description: Int
)