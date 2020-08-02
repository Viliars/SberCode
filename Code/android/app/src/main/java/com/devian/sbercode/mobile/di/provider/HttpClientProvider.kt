package com.devian.sbercode.mobile.di.provider

import com.devian.sbercode.mobile.network.interceptors.TokenInterceptor
import com.devian.sbercode.mobile.repository.local.ISettingsPreferences
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class HttpClientProvider @Inject constructor(
    private val settingsPreferences: ISettingsPreferences
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder().also {
            it.readTimeout(30, TimeUnit.SECONDS)
            it.addInterceptor(TokenInterceptor(settingsPreferences))
        }

        return builder.build()
    }
}