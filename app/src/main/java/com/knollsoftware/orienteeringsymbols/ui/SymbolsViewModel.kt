package com.knollsoftware.orienteeringsymbols.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.knollsoftware.orienteeringsymbols.SymbolsApplication
import com.knollsoftware.orienteeringsymbols.data.SymbolsRepository
import com.knollsoftware.orienteeringsymbols.model.Symbol
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.SearchWidgetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * ViewModel object to track user selections and UI statuses between screens and state changes
 */
class SymbolsViewModel(private val symbolsRepository: SymbolsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SymbolsUiState())
    val uiState: StateFlow<SymbolsUiState> = _uiState.asStateFlow()

    private val _searchWidgetState = mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState = _searchWidgetState

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _symbols = MutableStateFlow(symbolsRepository.getAllSymbols())
//    val symbols = _symbols
    val symbols = searchTextState
    .combine(_symbols) { query, symbols ->
        when {
            query.isNotEmpty() -> symbols.filter { symbol ->
                symbol.name.contains(query, ignoreCase = true) ||
                        symbol.description.contains(query, ignoreCase = true)
            }
            else -> symbols
        }
    }
    .stateIn(
        scope = viewModelScope,
        initialValue = _symbols.value,
        started = SharingStarted.WhileSubscribed(5000)
    )



    init {
//        val loadedSymbols = symbolsRepository.getAllSymbols()
        _uiState.update { currentState ->
            currentState.copy(
//                symbols = loadedSymbols,
                scrollPosition = 0,
                highlight = false
            )
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    /**
     * Update the scroll position to the selected symbol object
     *
     * @param selectedSymbol        Symbol object selected by the user
     */
    fun setScrollPosition(selectedSymbol: Symbol) {
        _uiState.update { currentState ->
//            currentState.copy(scrollPosition = currentState.symbols.indexOf(selectedSymbol))
            currentState.copy(scrollPosition = _symbols.value.indexOf(selectedSymbol))
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

    fun resetList() {
        _searchWidgetState.value = SearchWidgetState.CLOSED
        _searchTextState.value = ""
        _uiState.update { currentState ->
            currentState.copy(
                scrollPosition = 0,
                highlight = false
            )
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