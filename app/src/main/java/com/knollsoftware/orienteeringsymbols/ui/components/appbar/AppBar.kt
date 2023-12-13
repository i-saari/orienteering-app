package com.knollsoftware.orienteeringsymbols.ui.components.appbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import kotlinx.coroutines.launch

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
fun SymbolsAppBar(
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    @StringRes title: Int? = null,
    appBarActions: List<AppBarAction>? = null
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
                DrawerIcon(drawerState = drawerState)
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

/**
 * Composable of the navigation drawer icon
 *
 * @param drawerState       state of the navigation drawer
 */
@Composable
private fun DrawerIcon(drawerState: DrawerState) {
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

/**
 * Composable of a top app bar action button
 *
 * @param appBarAction      action button object with icon, description and action
 */
@Composable
fun AppBarAction(appBarAction: AppBarAction) {
    IconButton(onClick = appBarAction.onClick) {
        Icon(
            painter = painterResource(id = appBarAction.icon),
            modifier = Modifier.size(24.dp),
            contentDescription = stringResource(id = appBarAction.description)
        )
    }
}