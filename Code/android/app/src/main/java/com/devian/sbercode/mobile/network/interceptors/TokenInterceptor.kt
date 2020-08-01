package com.devian.sbercode.mobile.network.interceptors

import com.devian.sbercode.mobile.repository.local.ISettingsPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val settingsPreferences: ISettingsPreferences
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", settingsPreferences.authToken)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}