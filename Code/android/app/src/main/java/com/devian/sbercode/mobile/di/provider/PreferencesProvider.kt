package com.devian.sbercode.mobile.di.provider

import android.app.Application
import com.devian.sbercode.mobile.content.prefs.Preferences
import javax.inject.Inject
import javax.inject.Provider

class PreferencesProvider @Inject constructor(private val application: Application) :
    Provider<Preferences> {
    override fun get(): Preferences {
        return Preferences(
            application,
            "${application.packageName}_preferences_v1"
        )
    }
}