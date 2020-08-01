package com.devian.sbercode.mobile

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.devian.sbercode.mobile.di.Scopes
import com.devian.sbercode.mobile.di.module.AppModule
import com.devian.sbercode.mobile.di.module.ServerModule
import com.jakewharton.threetenabp.AndroidThreeTen
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.smoothie.module.SmoothieApplicationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initIoc()
        initThreeTen()
    }

    private fun initIoc() {
        if (Environment.isDebug) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction())
        }

        val appScope = Toothpick.openScope(Scopes.APP)
        appScope.installModules(SmoothieApplicationModule(this),
            AppModule,
            ServerModule
        )
    }

    private fun initThreeTen() {
        AndroidThreeTen.init(this)
    }

    object Environment {
        val isDebug = BuildConfig.BUILD_TYPE.equals("debug", false)
        val isStaging = BuildConfig.BUILD_TYPE.equals("staging", false)
        val isRelease = BuildConfig.BUILD_TYPE.equals("release", false)
    }
}