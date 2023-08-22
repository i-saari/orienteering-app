package com.example.orienteeringsymbols

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.orienteeringsymbols.data.DataSource.symbolGroups
import com.example.orienteeringsymbols.ui.SymbolsViewModel
import com.example.orienteeringsymbols.ui.StartScreen
import com.example.orienteeringsymbols.ui.ListScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orienteeringsymbols.ui.DetailsScreen
import com.example.orienteeringsymbols.ui.components.appbar.SymbolsAppBar
import com.example.orienteeringsymbols.ui.components.appdrawer.AppDrawerContent
import com.example.orienteeringsymbols.ui.components.appdrawer.AppDrawerItemInfo

enum class NavOptions(@StringRes val title: Int){
    List(title = R.string.landforms)
}

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            NavOptions.List,
            R.string.list,
            R.drawable.list,
            R.string.drawer_list_description
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
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = NavOptions.valueOf(
//        backStackEntry?.destination?.route ?: NavOptions.Start.name
//    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = NavOptions.List,
            ) { route ->
                when(route) {
                    NavOptions.List -> {
                        navController.navigate(route.name) {
                            popUpTo(NavOptions.List.name)
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
                ListScreen(drawerState)
            }
        }
    }
}


//Scaffold(
//topBar = {
//    SymbolsAppBar(
//        currentScreen = currentScreen,
//        canNavigateBack = navController.previousBackStackEntry != null,
//        navigateUp = { navController.navigateUp() })
//}
//) { innerPadding ->
//    val uiState by viewModel.uiState.collectAsState()
//    NavHost(
//        navController = navController,
//        startDestination = NavOptions.Start.name,
//        modifier = Modifier.padding(innerPadding)
//    ) {
//        composable(route = NavOptions.Start.name) {
//            StartScreen(
//                symbolGroups = symbolGroups,
//                onGroupButtonClicked = {
//                    viewModel.setGroup(it)
//                    navController.navigate(NavOptions.List.name)
//                }
//            )
//        }
//        composable(route = NavOptions.List.name) {
//            ListScreen(
//                symbolList = viewModel.getGroupSymbols(),
//                onSymbolClicked = {
//                    viewModel.setSymbol(it)
//                    navController.navigate(NavOptions.Details.name)
//                }
//            )
//        }
//        composable(route = NavOptions.Details.name) {
//            DetailsScreen(
//                symbol = uiState.symbol
//            )
//        }
//    }
//}