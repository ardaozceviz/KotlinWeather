package com.choxxy.rainmaker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RainmakerApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
