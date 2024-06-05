package com.knollsoftware.orienteeringsymbols.data

import android.content.Context

interface AppContainer {
    val symbolsRepository: SymbolsRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val symbolsRepository: SymbolsRepository by lazy {
        LocalSymbolsRepository(context)
    }
}