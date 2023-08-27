package com.example.orienteeringsymbols.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.orienteeringsymbols.R
import com.example.orienteeringsymbols.data.DataSource.symbols
import com.example.orienteeringsymbols.data.Symbol
import com.example.orienteeringsymbols.ui.components.appbar.SymbolsAppBar
import com.example.orienteeringsymbols.ui.components.appbar.SymbolsAppBarNoDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridScreen(
    drawerState: DrawerState,
    @StringRes title: Int,
    onSymbolClick: (Symbol) -> Unit
) {
    Scaffold(
        topBar = {
            SymbolsAppBar(
                drawerState = drawerState,
                title = title
            ) }
    ) {innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.image_size))
        ) {
            items(symbols) {
                SymbolGridItem(
                    symbol = it,
                    onSymbolClick = onSymbolClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolGridItem(
    symbol: Symbol,
    onSymbolClick: (Symbol) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.clickable { onSymbolClick(symbol) },
        contentScale = ContentScale.Crop,
        painter = painterResource(id = symbol.controlImageResourceId),
        contentDescription = stringResource(id = symbol.name)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridScreenNoDrawer(
    @StringRes title: Int
) {
    Scaffold(
        topBar = {
            SymbolsAppBarNoDrawer(
                title = title
            ) }
    ) {innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.image_size))
        ) {
            items(symbols) {
                SymbolGridItem(
                    symbol = it,
                    onSymbolClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun SymbolGridPreview(){
    GridScreenNoDrawer(title = R.string.list)
}