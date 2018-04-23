package com.volgoak.currency

import android.app.Application
import timber.log.Timber

/**
 * Created by alex on 4/23/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}