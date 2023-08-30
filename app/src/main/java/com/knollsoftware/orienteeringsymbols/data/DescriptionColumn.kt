package com.knollsoftware.orienteeringsymbols.data

import androidx.annotation.StringRes

/**
 * Class representing a header and body text of a control description column
 */
data class DescriptionColumn(
    @StringRes val title: Int,
    @StringRes val body: Int
)