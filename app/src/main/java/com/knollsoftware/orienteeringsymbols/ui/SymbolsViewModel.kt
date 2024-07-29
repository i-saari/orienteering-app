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
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.FilterItem
import com.knollsoftware.orienteeringsymbols.ui.components.appbar.FilterWidgetState
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
    private val _searchWidgetState = mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState = _searchWidgetState

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _filterWidgetState = mutableStateOf(FilterWidgetState.CLOSED)
    val filterWidgetState = _filterWidgetState

    private val _filterGroups = MutableStateFlow<List<FilterItem>>(
        symbolsRepository.getGroups().map { FilterItem(it, false) }
    )
    val filterGroups = _filterGroups.asStateFlow()

    private val _selectedSymbol = mutableStateOf<Symbol?>(null)
    val selectedSymbol = _selectedSymbol

    private val _highlight = mutableStateOf(false)
    val highlight = _highlight

    private val _symbols = MutableStateFlow(symbolsRepository.getAllSymbols())
    val symbols = searchTextState
        .combine(_symbols) { query, symbols ->
            query to symbols
        }
        .combine(_filterGroups) { (query, symbols), filterGroups ->
            // Filter buttons logic
            val filteredSymbols = if (filterGroups.any { it.selected }) {
                symbols.filter { symbol ->
                    filterGroups.any { it.selected && it.group == symbol.group }
                }
            } else {
                symbols
            }

            // Search query logic
            when {
                query.isNotEmpty() -> filteredSymbols.filter { symbol ->
                    symbol.name.contains(query, ignoreCase = true) ||
                            symbol.description.contains(query, ignoreCase = true)
                }

                else -> filteredSymbols
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = _symbols.value,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun toggleFilterWidgetState() {
        if (_filterWidgetState.value == FilterWidgetState.CLOSED) {
            _filterWidgetState.value = FilterWidgetState.OPENED
        } else {
            _filterWidgetState.value = FilterWidgetState.CLOSED
        }
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun toggleFilterChip(currentGroupItem: FilterItem) {
        val newGroupItem = FilterItem(currentGroupItem.group, !currentGroupItem.selected)
        _filterGroups.value = _filterGroups.value.mapButReplace(currentGroupItem, newGroupItem)
    }

    fun setSelectedSymbol(selectedSymbol: Symbol) {
        _selectedSymbol.value = selectedSymbol
        _highlight.value = true
    }

    fun resetList() {
        _searchWidgetState.value = SearchWidgetState.CLOSED
        _searchTextState.value = ""
        _filterWidgetState.value = FilterWidgetState.CLOSED
        _filterGroups.value = _filterGroups.value.map { it.copy(selected = false) }
        _selectedSymbol.value = null
        _highlight.value = false
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

    private fun <T> List<T>.mapButReplace(targetItem: T, newItem: T) = map {
        if (it == targetItem) {
            newItem
        } else {
            it
        }
    }

}