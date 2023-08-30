package com.knollsoftware.orienteeringsymbols.ui

import androidx.lifecycle.ViewModel
import com.knollsoftware.orienteeringsymbols.data.Symbol
import com.knollsoftware.orienteeringsymbols.data.SymbolsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel object to track user selections and UI statuses between screens and state changes
 */
class SymbolsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    /**
     * Update the uiState to store the user selected symbol object
     *
     * @param selectedSymbol        Symbol object selected by the user
     */
    fun setSymbol(selectedSymbol: Symbol) {
        _uiState.update { currentState ->
            currentState.copy(symbol = selectedSymbol)
        }
    }

    /**
     * Update the uiState to store whether the Symbol List entry should flash
     *
     * @param highlight             true indicates the item should flash
     */
    fun setHighlight(highlight: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(highlight = highlight)
        }
    }

}