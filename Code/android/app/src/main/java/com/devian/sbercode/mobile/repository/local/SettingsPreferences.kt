package com.devian.sbercode.mobile.repository.local

import com.devian.sbercode.mobile.content.prefs.Preferences
import javax.inject.Inject

class SettingsPreferences  @Inject constructor(
    private val preferences: Preferences
): ISettingsPreferences {
    override var authToken: String
        get() = preferences.getString(AUTH_TOKEN)
        set(value) = preferences.putString(AUTH_TOKEN, value)

    companion object {
        private const val AUTH_TOKEN = "auth_token"
    }
}