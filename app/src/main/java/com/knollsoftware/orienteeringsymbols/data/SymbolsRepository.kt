package com.knollsoftware.orienteeringsymbols.data

import android.content.Context
import android.util.Log
import com.knollsoftware.orienteeringsymbols.model.Symbol
import com.opencsv.CSVReader
import java.io.InputStreamReader

interface SymbolsRepository {
    fun getAllSymbols(): List<Symbol>
}

class LocalSymbolsRepository(context: Context) : SymbolsRepository {
    private val symbols: List<Symbol> = readSymbolData(context)

    override fun getAllSymbols(): List<Symbol> = symbols

    private fun readSymbolData(context: Context): List<Symbol> {
        val symbolList = mutableListOf<Symbol>()
        val assetManager = context.assets
        val inputStream = assetManager.open("SymbolData.csv")
        val reader = CSVReader(InputStreamReader(inputStream))

        reader.readNext() //skip header

        var line: Array<String>? = reader.readNext()
        while (line != null) {
            val symbol = Symbol(
                group = line[0],
                controlImageResourceId = context.resources.getIdentifier(
                    line[1],"drawable",context.packageName),
                name = line[2],
                description = line[3]
            )
            symbolList.add(symbol)
            line = reader.readNext()
        }
        reader.close()
        return symbolList
    }
}