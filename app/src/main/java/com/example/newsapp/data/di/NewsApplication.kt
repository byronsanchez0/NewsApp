package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(appModule)
        }
    }
}