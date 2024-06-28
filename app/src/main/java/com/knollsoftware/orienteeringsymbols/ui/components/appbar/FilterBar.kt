package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    filterItems: List<FilterItem>,
    onClick: (FilterItem) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp)
    ) {
        items(filterItems) {
            FilterChip(
                modifier = modifier
                    .padding(4.dp),
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

@Preview
@Composable
fun FilterBarPreview() {
    val items = listOf<FilterItem>(
        FilterItem("Landforms", false),
        FilterItem("Rock and Boulder", false),
        FilterItem("Water and Marsh", false)
    )
    
    FilterBar(
        filterItems = items,
        onClick = {}
    )
}