package com.example.iconfinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        // Branch logging for debugging
        Branch.enableTestMode()

        // Branch object initialization
        Branch.getAutoInstance(this)
        plant(Timber.DebugTree())
    }
}