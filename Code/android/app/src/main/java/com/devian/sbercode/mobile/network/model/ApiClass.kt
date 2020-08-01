package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

class ApiClass (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)