package com.knollsoftware.orienteeringsymbols.ui.screens

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import com.knollsoftware.orienteeringsymbols.data.DataSource.groupColor
import com.knollsoftware.orienteeringsymbols.data.DataSource.symbols
import com.knollsoftware.orienteeringsymbols.data.Symbol
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.SymbolsAppBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    drawerState: DrawerState,
    @StringRes title: Int,
    scrollPosition: Symbol,
    highlight: Boolean,
    resetList: () -> Unit
) {
    val animateDuration = 600L
    var triggerFlash by remember { mutableStateOf(false) }
    val baseColor = MaterialTheme.colorScheme.surface
    val flashColor = MaterialTheme.colorScheme.primary
    val animatedColor by animateColorAsState(
        targetValue = if (triggerFlash) baseColor else flashColor,
        animationSpec = keyframes {
            durationMillis = animateDuration.toInt()
            baseColor at 0
            baseColor at 200
            flashColor at 400
            baseColor at 600
        }
    )
    Scaffold(
        topBar = {
            SymbolsAppBar(
                drawerState = drawerState,
                title = title
        ) }
    ) { innerPadding ->
        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = symbols.indexOf(scrollPosition)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            state = listState
        ) {
            items(symbols) {
                val color = if (it == scrollPosition && highlight) {
                    animatedColor
                } else {
                    baseColor
                }
                SymbolListItem(
                    symbol = it,
                    color = color
                )

            }
        }
    }
    LaunchedEffect(Unit) {
        triggerFlash = !triggerFlash
        delay(animateDuration)
        resetList()
    }
}

@Composable
fun SymbolListItem(
    symbol: Symbol,
    color: Color,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = color
        )
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
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(3.dp)
                        .background(color = groupColor[symbol.group] ?: Color.Transparent)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Image(
                    modifier = modifier
                        .size(dimensionResource(R.dimen.image_size)),
//                        .padding(all = dimensionResource(id = R.dimen.padding_small)),
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = symbol.controlImageResourceId),
                    contentDescription = stringResource(id = symbol.name)
                )
                Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))) {
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
@Preview
@Composable
fun ListScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ListScreen(
        drawerState = drawerState,
        title = R.string.list,
        scrollPosition = symbols.first(),
        highlight = false
    ) {

    }
}