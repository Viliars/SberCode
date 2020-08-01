package com.devian.sbercode.mobile.network.model

import com.google.gson.annotations.SerializedName

data class ApiDailyInfo(

    @SerializedName("excellentReviews")
    val excellentReviews: Int,

    @SerializedName("badReviews")
    val badReviews: Int,

    @SerializedName("highlights")
    val highlights: List<ApiReview>,

    @SerializedName("topClasses")
    val topClasses: List<ApiTopClass>
)

data class ApiTopClass(

    @SerializedName("class")
    val _class: ApiClass,

    @SerializedName("count")
    val count: Int
)