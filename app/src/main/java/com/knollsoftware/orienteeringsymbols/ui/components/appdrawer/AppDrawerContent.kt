package com.knollsoftware.orienteeringsymbols.ui.components.appdrawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.knollsoftware.orienteeringsymbols.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Enum<T>> AppDrawerContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<T>>,
    onClick: (T) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler(
        enabled = drawerState.isOpen,
    ) {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalDrawerSheet(
        drawerShape = RoundedCornerShape(0.dp),
//        modifier = Modifier.wrapContentWidth()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    modifier = modifier
                        .padding(dimensionResource(id = R.dimen.padding_medium)),
                    painter = painterResource(id = R.drawable.title_banner),
                    contentDescription = stringResource(id = R.string.app_title)
                )
                LazyColumn(
//                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(menuItems) { item ->
//                        val selected = item.drawerOption.name == backStackEntry.value?.destination?.route
                        AppDrawerItem(item = item) { navOption ->
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onClick(navOption)
                        }
                    }
                }
            }
        }

    }
}