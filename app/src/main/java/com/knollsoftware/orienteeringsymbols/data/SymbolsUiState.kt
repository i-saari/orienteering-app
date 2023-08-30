package com.knollsoftware.orienteeringsymbols.data

import com.knollsoftware.orienteeringsymbols.data.DataSource.symbols

data class SymbolsUiState(
    /** Selected symbol, used for specifying the symbol list scrollto item */
    val symbol: Symbol = symbols.first(),
    /** Indicator to highlight list item when clicking a grid symbol */
    val highlight: Boolean = false
)