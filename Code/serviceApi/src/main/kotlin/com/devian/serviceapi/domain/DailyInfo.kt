package com.devian.serviceapi.domain

import com.google.gson.annotations.SerializedName

data class DailyInfoEntity(

        val excellentReviews: String,
        val badReviews: String,
        val highlights: List<Review>,
        val topClasses: List<TopClassEntity>
)

data class TopClassEntity(

        @SerializedName("class")
        val clazz: String,
        val count: Int
)