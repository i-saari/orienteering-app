package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    searchWidgetState: SearchWidgetState,
    drawerState: DrawerState? = null,
    @StringRes title: Int? = null,
    appBarActions: List<AppBarAction>? = null,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                modifier = modifier,
                drawerState = drawerState,
                title = title,
                appBarActions = appBarActions,
            )
        }
        SearchWidgetState.OPENED -> {
            SearchField(
                searchText = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

/**
 * Composable of the top app bar present in all screens. Contains the screen title,
 * the drawer navigation button, and any optional action buttons.
 *
 * @param drawerState       state of the navigation drawer
 * @param title             title to display in the top app bar
 * @param appBarActions     any optional action buttons other than the navigation drawer button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    @StringRes title: Int? = null,
    appBarActions: List<AppBarAction>? = null,
) {
    TopAppBar(
        title = {
                title?.let {
                    Text(
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
        },
        modifier = modifier,
        navigationIcon = {
            drawerState?.let {
                DrawerIcon(
                    drawerState = drawerState,
                )
            }
        },
        // Creates any action buttons
        actions = {
            appBarActions?.let {
                for (appBarAction in it) {
                    AppBarAction(appBarAction)
                }
            }
        }
    )
}

@Composable
fun SearchField(
    searchText: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchText,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.search_prompt),
                )
            },
//            textStyle = TextStyle(
//                fontSize = MaterialTheme.typography.bodySmall.fontSize
//            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = onCloseClicked) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.clear_icon_description)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(searchText) }
            ),
        )
    }
}

/**
 * Composable of the navigation drawer icon
 *
 * @param drawerState       state of the navigation drawer
 */
@Composable
private fun DrawerIcon(
    drawerState: DrawerState,
) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(onClick = {
        coroutineScope.launch {
            drawerState.open()
        }
    }) {
        Icon(
            Icons.Rounded.Menu,
            contentDescription = stringResource(id = R.string.drawer_icon_description)
        )
    }
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

/**
 * Composable of a top app bar action button
 *
 * @param appBarAction      action button object with icon, description and action
 */
@Composable
fun AppBarAction(appBarAction: AppBarAction) {
    IconButton(onClick = appBarAction.onClick) {
        Icon(
            imageVector = appBarAction.icon,
            contentDescription = appBarAction.description
        )
    }
}

@Composable
@Preview
fun DefaultAppBarPreview(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val actions = listOf<AppBarAction>(
        AppBarAction(
            icon = Icons.Rounded.Search,
            description = stringResource(R.string.search_icon_description),
            onClick = {}
        )
    )
    DefaultAppBar(
        drawerState = drawerState,
        title = R.string.list,
        appBarActions = actions,
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchField(
        searchText = "Search text",
        onTextChange = {},
        onCloseClicked = { },
        onSearchClicked = {}
    )
}