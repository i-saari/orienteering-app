package com.example.orienteeringsymbols.data

import com.example.orienteeringsymbols.data.DataSource.symbols

data class SymbolsUiState(
    /** Selected symbol */
    val symbol: Symbol = symbols.first()
)