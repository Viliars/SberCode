package com.devian.sbercode.mobile.domain.model

data class LoginResultEntity(
    val success: Boolean,
    val token: String,
    val error: String
)

data class LoginDataEntity(

    val organization:  String,
    val login: String,
    val passHash: String
)