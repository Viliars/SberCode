package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

data class ApiReview(

    @SerializedName("id")
    val id: Int,

    @SerializedName("date")
    val date: String,

    @SerializedName("app_id")
    val app_id: String,

    @SerializedName("app_name")
    val app_name: String,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("class")
    val _class: String
)

data class ApiWrongClass(

    @SerializedName("reviewId")
    val reviewId: Int,

    @SerializedName("rightClassId")
    val rightClassId: String
)