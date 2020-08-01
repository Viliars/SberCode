package com.devian.sbercode.mobile.di.module

import com.devian.sbercode.mobile.di.provider.HttpClientProvider
import com.devian.sbercode.mobile.di.provider.ServiceApiProvider
import com.devian.sbercode.mobile.network.ServiceApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import toothpick.config.Module

object ServerModule : Module() {
    init {
        bind(Gson::class.java).toInstance(Gson())
        bind(ServiceApi::class.java).toProvider(ServiceApiProvider::class.java).providesSingleton()
        bind(OkHttpClient::class.java).toProvider(HttpClientProvider::class.java)
            .providesSingleton()
    }
}