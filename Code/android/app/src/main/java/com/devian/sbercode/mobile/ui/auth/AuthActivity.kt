package com.devian.sbercode.mobile.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devian.sbercode.mobile.AuthListener
import com.devian.sbercode.mobile.MainActivity
import com.devian.sbercode.mobile.R

class AuthActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().add(R.id.container, AuthFragment()).commit()
    }

    override fun onAuthSuccess() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }
}