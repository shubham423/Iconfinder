package com.example.iconfinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        plant(Timber.DebugTree())
    }

    init {
        instance = this
    }
    companion object {
        private var instance: MyApplication? = null
        fun applicationContext(): MyApplication {
            return instance as MyApplication
        }
    }
}