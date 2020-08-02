package com.devian.serviceapi.domain

import com.google.gson.annotations.SerializedName

data class ApiResponse(

        @SerializedName("success")
        val success: Boolean
)