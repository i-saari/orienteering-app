package com.example.orienteeringsymbols

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.orienteeringsymbols.data.DataSource.symbolGroups
import com.example.orienteeringsymbols.ui.SymbolsViewModel
import com.example.orienteeringsymbols.ui.StartScreen
import com.example.orienteeringsymbols.ui.SymbolListScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orienteeringsymbols.data.DataSource.symbols

enum class SymbolScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    SymbolList(title = R.string.landforms)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolsAppBar(
    currentScreen: SymbolScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymbolsApp(
    viewModel: SymbolsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SymbolScreen.valueOf(
        backStackEntry?.destination?.route ?: SymbolScreen.Start.name
    )
    Scaffold(
        topBar = {
            SymbolsAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = SymbolScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = SymbolScreen.Start.name) {
                StartScreen(
                    symbolGroups = symbolGroups,
                    onGroupButtonClicked = {
                        viewModel.setGroup(it)
                        navController.navigate(SymbolScreen.SymbolList.name)
                    }
                )
            }
            composable(route = SymbolScreen.SymbolList.name) {
                SymbolListScreen(
                    symbolList = viewModel.getGroupSymbols()
                )
            }

        }
    }
}