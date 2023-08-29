package com.knollsoftware.orienteeringsymbols.data

import com.knollsoftware.orienteeringsymbols.data.DataSource.symbols

data class SymbolsUiState(
    /** Selected symbol */
    val symbol: Symbol = symbols.first(),
    /** Indicator to highlight list item */
    val highlight: Boolean = false
)