package com.devian.sbercode.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devian.sbercode.mobile.di.Scopes
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.ui.auth.AuthActivity
import toothpick.Toothpick

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsPreferences = Toothpick.openScope(Scopes.APP).getInstance(SettingsPreferences::class.java)
        val authToken = settingsPreferences.authToken

        if (authToken.isEmpty()) {
            startActivity(AuthActivity.getStartIntent(this))
        } else {
            startActivity(MainActivity.getStartIntent(this))
        }
    }
}

interface AuthListener {
    fun onAuthSuccess()
}