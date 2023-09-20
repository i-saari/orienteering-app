package com.knollsoftware.orienteeringsymbols

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            SymbolsApp(navController = navController)
        }
    }

    @Test
    fun symbolsNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(NavOptions.List.name)
    }

    @Test
    fun symbolsNavHost_clickNavigationButton_drawerOpens() {
        composeTestRule.onNodeWithContentDescriptionID(R.string.drawer_icon_description)
            .performClick()
        composeTestRule.onNodeWithContentDescriptionID(R.string.app_title)
            .assertIsDisplayed()
    }

    @Test
    fun symbolsNavHost_clickListOnDrawerFromListScreen_navigatesToListScreen() {

    }

    @Test
    fun symbolsNavHost_clickGridSymbol_navigatesToItemOnListScreen() {

    }
}