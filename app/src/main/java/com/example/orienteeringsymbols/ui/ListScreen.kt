package com.example.orienteeringsymbols.ui

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun ListScreen(
    drawerState: DrawerState,
    @StringRes title: Int
) {
    Scaffold(
        topBar = {
            SymbolsAppBar(
                drawerState = drawerState,
                title = title
        ) }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            items(symbols) {
                SymbolItem(
                    symbol = it,
//                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun SymbolItem(
    symbol: Symbol,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Image(
                    modifier = modifier.size(dimensionResource(R.dimen.image_size)),
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = symbol.controlImageResourceId),
                    contentDescription = stringResource(id = symbol.name)
                )
//            Image(
//                modifier = modifier,
//                contentScale = ContentScale.Crop,
//                painter = painterResource(id = symbol.mapImageResourceId),
//                contentDescription = stringResource(id = symbol.name)
//            )
                Column(modifier = Modifier) {
                    Text(
                        text = stringResource(id = symbol.name),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(id = symbol.group),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SymbolExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                SymbolDescription(
                    symbolDescription = symbol.description,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
fun SymbolExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(id = R.string.expand_button_content_description)
            // tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun SymbolDescription(
    @StringRes symbolDescription: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = symbolDescription),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenNoDrawer(
    @StringRes title: Int
) {
    Scaffold(
        topBar = {
            SymbolsAppBarNoDrawer(
                title = title
            ) }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            items(symbols) {
                SymbolItem(
                    symbol = it,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview
@Composable
fun SymbolListPreview(){
    ListScreenNoDrawer(title = R.string.list)
}