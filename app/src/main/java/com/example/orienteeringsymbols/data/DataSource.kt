package com.example.orienteeringsymbols.data

import android.provider.Settings.Global.getString
import androidx.compose.ui.res.stringResource
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.orienteeringsymbols.R

object DataSource {

    val symbolGroups = listOf(
        R.string.landforms,
        R.string.rock_and_boulder
    )

    val symbols = listOf(
        Symbol(R.string.landforms, R.drawable.reentrant_control, R.drawable.reentrant_map, R.string.reentrant_name, R.string.reentrant_description),
        Symbol(R.string.landforms, R.drawable.depression_control, R.drawable.depression_map, R.string.depression_name, R.string.depression_description),
        Symbol(R.string.rock_and_boulder, R.drawable.reentrant_control, R.drawable.reentrant_map, R.string.cliff, R.string.cliff_description),
        Symbol(R.string.rock_and_boulder, R.drawable.depression_control, R.drawable.depression_map, R.string.boulder, R.string.boulder_description)
    )
}