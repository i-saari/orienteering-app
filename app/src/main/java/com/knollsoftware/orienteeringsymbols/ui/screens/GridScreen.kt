package com.knollsoftware.orienteeringsymbols.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.knollsoftware.orienteeringsymbols.R
import com.knollsoftware.orienteeringsymbols.model.Symbol
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.SymbolsAppBar

/**
 * Composable to build the Grid screen. Displays a scrollable grid of symbol images and lets
 * the user jump to the symbol entry in the List screen by clicking on a symbol.
 *
 * @param drawerState           state of the navigation drawer
 * @param title                 title to display in the top app bar
 * @param onGridSymbolClick     action to occur when the symbol is clicked
 */
@Composable
fun GridScreen(
    symbols: List<Symbol>,
    drawerState: DrawerState,
    @StringRes title: Int,
    onGridSymbolClick: (Symbol) -> Unit
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
                    onGridSymbolClick = onGridSymbolClick
                )
            }
        }
    }
}

/**
 * Composable of an individual symbol to display in the grid
 *
 * @param symbol                Symbol option to display
 * @param onGridSymbolClick     action to occur when the symbol is clicked
 */
@Composable
fun SymbolGridItem(
    symbol: Symbol,
    onGridSymbolClick: (Symbol) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.clickable { onGridSymbolClick(symbol) },
        contentScale = ContentScale.Crop,
        painter = painterResource(id = symbol.controlImageResourceId),
        contentDescription = symbol.name
    )
}

//@Preview
//@Composable
//fun SymbolGridPreview(){
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    GridScreen(drawerState = drawerState, title = R.string.list, onGridSymbolClick = {})
//}