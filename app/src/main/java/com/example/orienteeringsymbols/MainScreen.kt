package com.example.orienteeringsymbols

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.orienteeringsymbols.ui.DescriptionScreen
import com.example.orienteeringsymbols.ui.GridScreen
import com.example.orienteeringsymbols.ui.ListScreen
import com.example.orienteeringsymbols.ui.SymbolsViewModel
import com.example.orienteeringsymbols.ui.components.appdrawer.AppDrawerContent
import com.example.orienteeringsymbols.ui.components.appdrawer.AppDrawerItemInfo

enum class NavOptions(@StringRes val title: Int){
    List(title = R.string.list),
    Grid(title = R.string.grid),
    Description(title = R.string.description)
}

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            NavOptions.List,
            R.string.list,
            R.drawable.list,
            R.string.drawer_list_description
        ),
        AppDrawerItemInfo(
            NavOptions.Grid,
            R.string.grid,
            R.drawable.grid,
            R.string.drawer_grid_description
        ),
        AppDrawerItemInfo(
            NavOptions.Description,
            R.string.description,
            R.drawable.control_description,
            R.string.drawer_description_description
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolsApp(
    viewModel: SymbolsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
            ) { route ->
                when(route) {
                    NavOptions.List -> {
                        navController.navigate(route.name) {
                            popUpTo(NavOptions.List.name)
                        }
                    }
                    NavOptions.Grid -> {
                        navController.navigate(route.name) {
                            popUpTo(NavOptions.Grid.name)
                        }
                    }
                    NavOptions.Description -> {
                        navController.navigate(route.name) {
                            popUpTo(NavOptions.Description.name)
                        }
                    }
                }
            }
        }
    ) {

        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = NavOptions.List.name,
            modifier = Modifier
        ) {
            composable(route = NavOptions.List.name) {
                ListScreen(
                    drawerState = drawerState,
                    title = NavOptions.List.title,
                    scrollPosition = uiState.symbol
                )
            }
            composable(route = NavOptions.Grid.name) {
                GridScreen(
                    drawerState = drawerState,
                    title = NavOptions.Grid.title,
                    onGridSymbolClick = {
                        viewModel.setSymbol(it)
                        navController.navigate(NavOptions.List.name)
                    }
                )
            }
            composable(route = NavOptions.Description.name) {
                DescriptionScreen(drawerState = drawerState, title = NavOptions.Description.title)
            }
        }
    }
}