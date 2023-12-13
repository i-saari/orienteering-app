package com.knollsoftware.orienteeringsymbols.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knollsoftware.orienteeringsymbols.data.DataSource.allSymbols
import com.knollsoftware.orienteeringsymbols.data.Symbol
import com.knollsoftware.orienteeringsymbols.data.SymbolsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * ViewModel object to track user selections and UI statuses between screens and state changes
 */
class SymbolsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _symbols = MutableStateFlow(allSymbols)

    val symbols = searchText
//        .debounce(1000L)
//        .onEach {  }
        .combine(_symbols) { text, symbols ->
            if(text.isBlank()) {
                symbols
            } else {
                symbols.filter {
                    it.doesMatchSearchQuery(query = text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _symbols.value
        )

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