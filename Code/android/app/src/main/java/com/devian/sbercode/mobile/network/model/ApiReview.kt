package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

data class ApiReview(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("app_id")
    val app_id: String,

    @SerializedName("app_name")
    val app_name: String,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("text")
    val text: String?,

    @SerializedName("class")
    val _class: ApiClass
)

data class ApiWrongClass(

    @SerializedName("review")
    val review: ApiReview,

    @SerializedName("rightClass")
    val rightClassId: String
)