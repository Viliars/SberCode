package com.devian.sbercode.mobile.repository.local

import com.devian.sbercode.mobile.content.prefs.Preferences
import com.devian.sbercode.mobile.domain.model.ReviewClassEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class SettingsPreferences  @Inject constructor(
    private val preferences: Preferences,
    private val gson: Gson
): ISettingsPreferences {
    override var authToken: String
        get() = preferences.getString(AUTH_TOKEN)
        set(value) = preferences.putString(AUTH_TOKEN, value)

    override var reviewClasses: List<ReviewClassEntity>
        get() = gson.fromJson<List<ReviewClassEntity>>(preferences.getString(REVIEW_CLASSES), object : TypeToken<List<ReviewClassEntity>>() {}.type)
        set(value) = preferences.putString(REVIEW_CLASSES, gson.toJson(value))

    companion object {
        private const val AUTH_TOKEN = "auth_token"
        private const val REVIEW_CLASSES = "review_classes"
    }
}