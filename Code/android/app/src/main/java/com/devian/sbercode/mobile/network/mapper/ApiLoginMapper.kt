package com.devian.sbercode.mobile.network.mapper

import com.devian.sbercode.mobile.domain.model.LoginDataEntity
import com.devian.sbercode.mobile.domain.model.LoginResultEntity
import com.devian.sbercode.mobile.network.model.ApiLoginData
import com.devian.sbercode.mobile.network.model.ApiLoginResult
import javax.inject.Inject

class ApiLoginMapper @Inject constructor() {

    fun transform(apiLoginResult: ApiLoginResult): LoginResultEntity {
        return LoginResultEntity(
            success = apiLoginResult.success,
            token = apiLoginResult.token,
            error = apiLoginResult.error
        )
    }

    fun transform(loginResultEntity: LoginResultEntity): ApiLoginResult {
        return ApiLoginResult(
            success = loginResultEntity.success,
            token = loginResultEntity.token,
            error = loginResultEntity.error
        )
    }

    fun transform(apiLoginData: ApiLoginData): LoginDataEntity {
        return LoginDataEntity(
            organization = apiLoginData.organization,
            login = apiLoginData.login,
            passHash = apiLoginData.passHash
        )
    }

    fun transform(loginDataEntity: LoginDataEntity): ApiLoginData {
        return ApiLoginData(
            organization = loginDataEntity.organization,
            login = loginDataEntity.login,
            passHash = loginDataEntity.passHash
        )
    }
}