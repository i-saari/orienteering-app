package com.knollsoftware.orienteeringsymbols

import android.app.Application
import com.knollsoftware.orienteeringsymbols.data.AppContainer
import com.knollsoftware.orienteeringsymbols.data.DefaultAppContainer

class SymbolsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}