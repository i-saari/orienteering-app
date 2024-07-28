package com.knollsoftware.orienteeringsymbols.ui.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import com.knollsoftware.orienteeringsymbols.data.DataSource.groupColor
import com.knollsoftware.orienteeringsymbols.model.Symbol
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

/**
 * Composable to build the List screen. Displays a scrollable list of symbols showing the symbol
 * image, title and group. Clicking a list entry will expand the item to show the symbol
 * description.
 *
 * @param drawerState           state of the navigation drawer
 * @param title                 title to display in the top app bar
 * @param scrollPosition        Symbol to automatically scroll to on composition
 * @param highlight             indicates whether the scrolled to item should flash on composition
 * @param resetSelection             action(s) to perform a reset of the ViewModel selected symbol to
 *                              avoid the list from scrolling and flashing the previously selected
 *                              item after the user has navigated away
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    listAppBar: @Composable () -> Unit,
    symbols: List<Symbol>,
    scrollPosition: Int,
    selectedSymbol: Symbol?,
    highlight: Boolean,
    resetSelection: () -> Unit,
) {
    val groupedSymbols = symbols.groupBy { it.group }
    var foundIndex = 0
    var groupIndex = 0
    if (selectedSymbol != null) {
        for ((group, symbolList) in groupedSymbols) {
            if (symbolList.contains(selectedSymbol)) {
                foundIndex += symbolList.indexOf(selectedSymbol)
                break
            } else {
                foundIndex += symbolList.size
                groupIndex += 1
            }
        }
    }

    val groupHeaderStyle = MaterialTheme.typography.bodyLarge
//    val headerStyleHeight = with(
//        LocalDensity.current) { groupHeaderStyle.fontSize.value.toDp() }
//    val offsetDp = (headerStyleHeight + dimensionResource(R.dimen.padding_small))
//    val offsetPx = with(LocalDensity.current) { offsetDp.toPx() }
//    Log.d("Debug","Style height: $headerStyleHeight")
//    Log.d("Debug","Offset Dp: $offsetDp")
//    Log.d("Debug","Offset Px: $offsetPx")



    // Defines the list item flash animation
    val animateDuration = 600L
    var triggerFlash by remember { mutableStateOf(false) }
    val baseColor = MaterialTheme.colorScheme.surface
    val flashColor = MaterialTheme.colorScheme.primary
    val animatedColor by animateColorAsState(
        targetValue = if (triggerFlash) baseColor else flashColor,
        label = "listFlash",
        animationSpec = keyframes {
            durationMillis = animateDuration.toInt()
            baseColor at 0
            baseColor at (animateDuration * 1 / 3).toInt()
            flashColor at (animateDuration * 2 / 3).toInt()
            baseColor at (animateDuration * 3 / 3).toInt()
        }
    )

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var componentHeight by remember { mutableStateOf(0) }

    val density = LocalDensity.current

    LaunchedEffect(density) {
        coroutineScope.launch {
            listState.animateScrollToItem(
                index = foundIndex + groupIndex + 1,
//                index = 2
                scrollOffset = -componentHeight
            )
        }
    }

    Scaffold(
        topBar = {
            listAppBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            state = listState
        ) {
            groupedSymbols.forEach { (group, symbols) ->
                stickyHeader {
                    GroupHeader(
                        group,
                        groupHeaderStyle,
                        modifier = Modifier.onGloballyPositioned {
                            componentHeight = with(density) {
                                it.size.height
                            }
                            Log.d("debug", "height: $componentHeight")
                        }
                    )
                }
                items(symbols) { symbol ->
                    val color = if (symbol == selectedSymbol && highlight) {
                        animatedColor
                    } else {
                        baseColor
                    }
                    SymbolListItem(
                        symbol = symbol,
                        color = color,
                        loadExpanded = (symbol == selectedSymbol)
                    )
                }
            }
        }

    }

    // Trigger the list item flash and reset the ViewModel selected symbol
    LaunchedEffect(Unit) {
        triggerFlash = !triggerFlash
        delay(animateDuration)
        resetSelection()
    }
}

@Composable
fun GroupHeader(
    group: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = group,
        style = style,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(dimensionResource(R.dimen.padding_small))
    )
}

/**
 * Composable of a symbol list item
 *
 * @param symbol        Symbol object to display
 * @param color         color of item background, included to allow flash animation
 */
@Composable
fun SymbolListItem(
    symbol: Symbol,
    color: Color,
    modifier: Modifier = Modifier,
    loadExpanded: Boolean = false,
) {
    // State of list item expansion that displays the symbol description
    var expanded by remember { mutableStateOf(loadExpanded) }
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
                // box used to build coloured vertical line
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
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = symbol.controlImageResourceId),
                    contentDescription = symbol.name
                )
                Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))) {
                    Text(
                        text = symbol.name,
                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                    )
//                    Text(
//                        text = symbol.group,
//                        style = MaterialTheme.typography.titleMedium,
//                        modifier = Modifier
//                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                SymbolExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            // Includes symbol description if list item is expanded
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

/**
 * Composable of the list item expansion button
 *
 * @param expanded          state of item expansion to display correct button orientation
 * @param onClick           action to perform when the button is clicked
 */
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

/**
 * Composable of the symbol description to be displayed when list item is expanded
 *
 * @param symbolDescription     description text to be displayed
 */
@Composable
fun SymbolDescription(
    symbolDescription: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = symbolDescription,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Preview
@Composable
fun ListScreenPreview() {
    val previewSymbols = listOf<Symbol>(
        Symbol("Landforms", R.drawable.terrace_control, "Terrace", "Description"),
        Symbol("Landforms", R.drawable.spur_control, "Spur", "Description"),
        Symbol("Landforms", R.drawable.reentrant_control, "Reentrant", "Description"),
    )

    ListScreen(
        listAppBar = {},
        symbols = previewSymbols,
        selectedSymbol = previewSymbols[0],
        scrollPosition = 0,
        highlight = false,
        resetSelection = {},
    )
}