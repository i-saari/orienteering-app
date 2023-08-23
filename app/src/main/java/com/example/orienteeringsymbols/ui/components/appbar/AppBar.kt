package com.example.orienteeringsymbols.ui.components.appbar

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
import com.example.orienteeringsymbols.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolsAppBar(
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
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
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        ),
        actions = {
            appBarActions?.let {
                for (appBarAction in it) {
                    AppBarAction(appBarAction)
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (drawerState != null && navigationIcon == null) {
                DrawerIcon(drawerState = drawerState)
            } else {
                navigationIcon?.invoke()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolsAppBarNoDrawer(
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
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
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        ),
        actions = {
            appBarActions?.let {
                for (appBarAction in it) {
                    AppBarAction(appBarAction)
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            navigationIcon?.invoke()
        }
    )
}