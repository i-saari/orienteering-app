package com.knollsoftware.orienteeringsymbols.ui.search

import android.content.Context
import androidx.compose.runtime.Composable

interface Searchable {
    @Composable
    fun doesMatchSearchQuery(query: String): Boolean
}