package com.knollsoftware.orienteeringsymbols.ui

import com.knollsoftware.orienteeringsymbols.model.Symbol

data class SymbolsUiState(
    /** List of symbols to display */
//    val symbols: List<Symbol> = emptyList(),
    /** Selected symbol, used for specifying the symbol list scroll to item */
    val scrollPosition: Int = 0,
    /** Indicator to highlight list item when clicking a grid symbol */
    val highlight: Boolean = false
)