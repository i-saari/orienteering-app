package com.knollsoftware.orienteeringsymbols

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.knollsoftware.orienteeringsymbols.ui.SymbolsViewModel
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.AppBarAction
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.FilterWidgetState
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.SearchAppBar
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.SearchWidgetState
import com.knollsoftware.orienteeringsymbols.ui.components.appdrawer.AppDrawerContent
import com.knollsoftware.orienteeringsymbols.ui.components.appdrawer.AppDrawerItemInfo
import com.knollsoftware.orienteeringsymbols.ui.screens.AboutScreen
import com.knollsoftware.orienteeringsymbols.ui.screens.DescriptionScreen
import com.knollsoftware.orienteeringsymbols.ui.screens.GridScreen
import com.knollsoftware.orienteeringsymbols.ui.screens.ListScreen

/**
 * Available navigation routes with the title to be passed to the app top bar
 */
enum class NavOptions(@StringRes val title: Int) {
    List(title = R.string.list),
    Grid(title = R.string.grid),
    Description(title = R.string.description),
    About(title = R.string.about)
}

/**
 * Contains a list of navigation options to populate the navigation drawer
 */
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
        ),
        AppDrawerItemInfo(
            NavOptions.About,
            R.string.about,
            R.drawable.info,
            R.string.drawer_about_description
        )
    )
}

/**
 * Main composable run at startup. Provides the foundation for the app. The navigation drawer is
 * the parent composable of the app, with the screens built within it.
 *
 * @param navController         navigation controller for switching screens
 * @param drawerState           state of the navigation drawer
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SymbolsApp(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val symbolsViewModel: SymbolsViewModel = viewModel(factory = SymbolsViewModel.Factory)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
            ) { route ->
                // actions performed when the user clicks the drawer navigation options
                symbolsViewModel.resetList()
                when (route) {
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

                    NavOptions.About -> {
                        navController.navigate(route.name) {
                            popUpTo(NavOptions.About.name)
                        }
                    }
                }
            }
        }
    ) {
        val uiState by symbolsViewModel.uiState.collectAsState()
        val symbols by symbolsViewModel.symbols.collectAsState()
        val searchWidgetState by symbolsViewModel.searchWidgetState
        val searchTextState by symbolsViewModel.searchTextState.collectAsState()
        val filterWidgetState by symbolsViewModel.filterWidgetState
        val filterItems by symbolsViewModel.filterGroups.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current

        val listAppBarActions = listOf<AppBarAction>(
            AppBarAction(
                icon = Icons.Rounded.Search,
                description = stringResource(R.string.search_icon_description),
                onClick = {
                    symbolsViewModel.updateSearchWidgetState(
                        SearchWidgetState.OPENED
                    )
                }
            ),
            AppBarAction(
                icon = Icons.Rounded.FilterList,
                description = stringResource(R.string.filter_icon_description),
                onClick = { symbolsViewModel.toggleFilterWidgetState() }
            )
        )

        NavHost(
            navController = navController,
            startDestination = NavOptions.List.name,
            modifier = Modifier
        ) {
            composable(route = NavOptions.List.name) {
                ListScreen(
                    listAppBar = {
                        SearchAppBar(
                            drawerState = drawerState,
                            title = NavOptions.List.title,
                            appBarActions = listAppBarActions,
                            searchWidgetState = searchWidgetState,
                            filterWidgetState = filterWidgetState,
                            searchTextState = searchTextState,
                            onTextChange = { symbolsViewModel.updateSearchTextState(it) },
                            onCloseClicked = {
                                symbolsViewModel.updateSearchTextState("")
                                symbolsViewModel.updateSearchWidgetState(
                                    SearchWidgetState.CLOSED
                                )
                            },
                            onSearchClicked = { keyboardController?.hide() },
                            filterItems = filterItems,
                            onFilterClick = { item ->
                                symbolsViewModel.toggleFilterChip(item)
                            }
                        )
                    } ,
                    symbols = symbols,
                    scrollPosition = uiState.scrollPosition,
                    highlight = uiState.highlight,
                    resetSelection = {
                        /* reset the selected symbol to the top of the list after flashing the item
                        so that it won't flash again if the user navigates away and then back
                        */
                        symbolsViewModel.resetList()
                    },
                )
            }
            composable(route = NavOptions.Grid.name) {
                GridScreen(
                    drawerState = drawerState,
                    title = NavOptions.Grid.title,
                    symbols = symbols,
                    onGridSymbolClick = {
                        symbolsViewModel.setScrollPosition(it)
                        symbolsViewModel.setHighlight(true)
                        navController.navigate(NavOptions.List.name)
                    }
                )
            }
            composable(route = NavOptions.Description.name) {
                DescriptionScreen(drawerState = drawerState, title = NavOptions.Description.title)
            }
            composable(route = NavOptions.About.name) {
                AboutScreen(drawerState = drawerState, title = NavOptions.About.title)
            }
        }
    }
}