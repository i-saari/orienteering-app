package com.example.orienteeringsymbols.ui

import androidx.lifecycle.ViewModel
import com.example.orienteeringsymbols.data.DataSource.symbols
import com.example.orienteeringsymbols.data.Symbol
import com.example.orienteeringsymbols.data.SymbolsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SymbolsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    fun setGroup(selectedGroup: Int) {
        _uiState.update { currentState ->
            currentState.copy(group = selectedGroup)
        }
    }

    fun getGroupSymbols(): List<Symbol> {
        return symbols.filter { it.group == _uiState.value.group }
    }

    fun setSymbol(selectedSymbol: Symbol) {
        _uiState.update { currentState ->
            currentState.copy(symbol = selectedSymbol)
        }
    }


}