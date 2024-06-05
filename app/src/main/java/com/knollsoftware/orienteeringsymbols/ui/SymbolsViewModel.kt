package com.knollsoftware.orienteeringsymbols.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.knollsoftware.orienteeringsymbols.SymbolsApplication
import com.knollsoftware.orienteeringsymbols.data.SymbolsRepository
import com.knollsoftware.orienteeringsymbols.model.Symbol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel object to track user selections and UI statuses between screens and state changes
 */
class SymbolsViewModel(private val symbolsRepository: SymbolsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    init {
        val loadedSymbols = symbolsRepository.getAllSymbols()
        _uiState.update { currentState ->
            currentState.copy(
                symbols = loadedSymbols,
                scrollPosition = 0,
                highlight = false
            )
        }
    }

    /**
     * Update the scroll position to the selected symbol object
     *
     * @param selectedSymbol        Symbol object selected by the user
     */
    fun setScrollPosition(selectedSymbol: Symbol) {
        _uiState.update { currentState ->
            currentState.copy(scrollPosition = currentState.symbols.indexOf(selectedSymbol))
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

    fun resetScrolling() {
        _uiState.update { currentState ->
            currentState.copy(scrollPosition = 0, highlight = false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SymbolsApplication)
                val symbolsRepository = application.container.symbolsRepository
                SymbolsViewModel(symbolsRepository = symbolsRepository)
            }
        }
    }

}