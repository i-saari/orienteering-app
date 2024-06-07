package com.knollsoftware.orienteeringsymbols.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.knollsoftware.orienteeringsymbols.R
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.DefaultAppBar

/**
 * Composable to build the About screen. The screen simply displays credit text.
 *
 * @param drawerState       state of the navigation drawer
 * @param title             title to display in the top app bar
 */
@Composable
fun AboutScreen(
    drawerState: DrawerState,
    @StringRes title: Int
) {
    Scaffold(
        topBar = {
            DefaultAppBar(
                drawerState = drawerState,
                title = title,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = stringResource(id = R.string.credit_body),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    AboutScreen(drawerState = drawerState, title = R.string.about)
}