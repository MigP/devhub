package com.migpapps.devhub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DevHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialisation code here
    }
}