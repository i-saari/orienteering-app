package com.knollsoftware.orienteeringsymbols

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.knollsoftware.orienteeringsymbols.ui.theme.OrienteeringSymbolsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrienteeringSymbolsTheme {
                SymbolsApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SymbolsAppPreview() {
    OrienteeringSymbolsTheme {
        SymbolsApp()
    }
}