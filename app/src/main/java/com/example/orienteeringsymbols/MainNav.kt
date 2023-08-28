package com.example.orienteeringsymbols

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.orienteeringsymbols.data.Symbol
import com.example.orienteeringsymbols.data.SymbolsUiState
import com.example.orienteeringsymbols.ui.DescriptionScreen
import com.example.orienteeringsymbols.ui.GridScreen
import com.example.orienteeringsymbols.ui.ListScreen
import com.example.orienteeringsymbols.ui.SymbolsViewModel

//@OptIn(ExperimentalMaterial3Api::class)
//fun NavGraphBuilder.mainGraph(
//    drawerState: DrawerState,
//    uiState: SymbolsUiState,
//    onGridSymbolClick: (Symbol) -> Unit
//) {
//
//    navigation(
//        startDestination = NavOptions.List.name,
//        route = NavRoutes.MainRoute.name
//    ) {
//        composable(route = NavOptions.List.name) {
//            ListScreen(
//                drawerState = drawerState,
//                title = NavOptions.List.title,
//                scrollPosition = uiState.symbol
//            )
//        }
//        composable(route = NavOptions.Grid.name) {
//            GridScreen(
//                drawerState = drawerState,
//                title = NavOptions.Grid.title,
//                onGridSymbolClick = onGridSymbolClick
//            )
//        }
//        composable(route = NavOptions.Description.name) {
//            DescriptionScreen(drawerState = drawerState, title = NavOptions.Description.title)
//        }
//    }
//}