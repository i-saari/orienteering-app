package com.example.orienteeringsymbols.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.orienteeringsymbols.R
import com.example.orienteeringsymbols.data.Symbol

@Composable
fun ListScreen(
    symbolList: List<Symbol>,
    onSymbolClicked: (Symbol) -> Unit
) {
    LazyColumn() {
        items(symbolList) {
            SymbolItem(
                symbol = it,
                onSymbolClicked = onSymbolClicked,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolItem(
    symbol: Symbol,
    onSymbolClicked: (Symbol) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        onClick = { onSymbolClicked(symbol) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = symbol.controlImageResourceId),
                contentDescription = stringResource(id = symbol.name)
            )
            Image(
                modifier = modifier,
                contentScale = ContentScale.Crop,
                painter = painterResource(id = symbol.mapImageResourceId),
                contentDescription = stringResource(id = symbol.name)
            )
            Text(
                text = stringResource(id = symbol.name),
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
            )
        }
    }
}
