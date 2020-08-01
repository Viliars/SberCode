package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

data class ApiLoginResult(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("token")
    val token: String,

    @SerializedName("error_info")
    val error: String
)

data class ApiLoginData(
    @SerializedName("organization")
    val organization:  String,

    @SerializedName("login")
    val login: String,

    @SerializedName("passHash")
    val passHash: String
)