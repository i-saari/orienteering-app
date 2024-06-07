package com.knollsoftware.orienteeringsymbols.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import com.knollsoftware.orienteeringsymbols.data.DataSource.descriptionColumns
import com.knollsoftware.orienteeringsymbols.data.DataSource.specialInstructions
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.DefaultAppBar

/**
 * Composable to build the Control Description screen. The screen displays text and images
 * describing how to read a control description.
 *
 * @param drawerState       state of the navigation drawer
 * @param title             title to display in the top app bar
 */
@Composable
fun DescriptionScreen(
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.con_des_intro),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .fillMaxWidth()
                )
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.example_control_description_labeled),
                    contentDescription = stringResource(id = R.string.example_control_description_labeled),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(370.dp)
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.con_des_columns_description),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(id = R.string.con_des_columns),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .fillMaxWidth()
                    )
                }
            }
            item {
                Image(
                    painter = painterResource(id = R.drawable.control_description_columns),
                    contentDescription = stringResource(id = R.string.con_des_columns_description),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(250.dp)
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
            items(descriptionColumns) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(it.title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(it.body),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.con_des_spec_title),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                        Text(
                            text = stringResource(id = R.string.con_des_spec_body),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.padding_small))
                                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                        )
                    }
                }
            }
            items(specialInstructions) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = it.imageResourceId),
                        contentDescription = stringResource(id = it.description),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(it.body),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DescriptionScreenPreview(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    DescriptionScreen(drawerState = drawerState, title = R.string.description)
}