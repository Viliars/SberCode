package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @SerializedName("success")
    val success: Boolean
)