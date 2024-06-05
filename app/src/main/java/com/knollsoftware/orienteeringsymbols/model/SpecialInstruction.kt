package com.knollsoftware.orienteeringsymbols.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Class representing the image, header and body text of a control description special instruction line
 */
data class SpecialInstruction(
    @DrawableRes val imageResourceId: Int,
    @StringRes val body: Int,
    @StringRes val description: Int
)