package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    filterItems: List<FilterItem>,
    onClick: (FilterItem) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        items(filterItems) {
            FilterChip(
                selected = it.selected,
                onClick = { onClick(it) },
                label = { Text(it.group) }
            )
        }
    }
}

data class FilterItem (
    val group: String,
    val selected: Boolean
)