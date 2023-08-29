package com.knollsoftware.orienteeringsymbols.ui

import androidx.lifecycle.ViewModel
import com.knollsoftware.orienteeringsymbols.data.Symbol
import com.knollsoftware.orienteeringsymbols.data.SymbolsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SymbolsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    fun setSymbol(selectedSymbol: Symbol) {
        _uiState.update { currentState ->
            currentState.copy(symbol = selectedSymbol)
        }
    }

    fun setHighlight(highlight: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(highlight = highlight)
        }
    }

}