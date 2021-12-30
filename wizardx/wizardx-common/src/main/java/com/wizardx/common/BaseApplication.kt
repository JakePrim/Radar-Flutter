package com.wizardx.common

import android.app.Application
import android.content.Context

class BaseApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        currentApplication = this
    }

    companion object {
        @JvmStatic
        @get:JvmName("currentApplication")
        lateinit var currentApplication: Application
            private set
    }
}