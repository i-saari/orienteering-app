package com.example.orienteeringsymbols.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.orienteeringsymbols.R

data class Symbol(
    @DrawableRes val controlImageResourceId: Int,
    @DrawableRes val mapImageResourceId: Int,
    @StringRes val name: Int,
    @StringRes val description: Int
)

val symbols = listOf(
    Symbol(R.drawable.reentrant_control, R.drawable.reentrant_map, R.string.reentrant_name, R.string.reentrant_description),
    Symbol(R.drawable.depression_control, R.drawable.depression_map, R.string.depression_name, R.string.depression_description)
)