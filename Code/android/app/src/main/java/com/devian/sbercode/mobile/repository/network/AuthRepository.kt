package com.devian.sbercode.mobile.repository.network

import com.devian.sbercode.mobile.domain.model.LoginDataEntity
import com.devian.sbercode.mobile.domain.model.LoginResultEntity
import com.devian.sbercode.mobile.network.ServiceApi
import com.devian.sbercode.mobile.network.mapper.ApiLoginMapper
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val gson: Gson,
    private val serviceApi: ServiceApi,
    private val apiLoginMapper: ApiLoginMapper
) {

    fun login(loginDataEntity: LoginDataEntity): Single<LoginResultEntity> {
        return serviceApi.login(loginDataEntity).map {
            apiLoginMapper.transform(it)
        }
    }
}