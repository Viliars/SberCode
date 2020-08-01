package com.devian.sbercode.mobile.di.module

import com.devian.sbercode.mobile.content.prefs.Preferences
import com.devian.sbercode.mobile.di.provider.PreferencesProvider
import com.devian.sbercode.mobile.network.mapper.ApiReviewMapper
import com.devian.sbercode.mobile.repository.local.ISettingsPreferences
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.repository.network.AuthRepository
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import toothpick.config.Module

object AppModule : Module() {
    init {

        // Basics
        bind(Preferences::class.java).toProvider(PreferencesProvider::class.java)
            .providesSingleton()

        // Preferences
        bind(ISettingsPreferences::class.java).to(SettingsPreferences::class.java).singleton()

        // Repository
        bind(ReviewsRepository::class.java).singleton()
        bind(AuthRepository::class.java).singleton()
    }
}