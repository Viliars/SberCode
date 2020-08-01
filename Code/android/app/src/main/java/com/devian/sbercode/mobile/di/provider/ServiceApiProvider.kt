package com.devian.sbercode.mobile.di.provider

import com.devian.sbercode.mobile.App
import com.devian.sbercode.mobile.network.ServiceApi
import com.devian.sbercode.mobile.network.ServiceApiMock
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class ServiceApiProvider @Inject constructor(
    private val gson: Gson,
    private val okHttpClient: OkHttpClient
) : Provider<ServiceApi> {
    override fun get(): ServiceApi {
        val endpoint = createEndpoint()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(endpoint)
            .build()

        //return retrofit.create(ServiceApi::class.java)
        return ServiceApiMock()
    }

    private fun createEndpoint(): String {
        return when {
            App.Environment.isRelease -> ENDPOINT_PRODUCTION
            else -> ENDPOINT_DEVELOP
        }
    }

    companion object {
        private const val ENDPOINT_DEVELOP = "https://10.0.2.2:8080/"
        private const val ENDPOINT_PRODUCTION = "https://devian.ws.pho.to/sbercode/"
    }
}