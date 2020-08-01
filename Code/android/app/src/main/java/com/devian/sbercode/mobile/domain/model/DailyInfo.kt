package com.devian.sbercode.mobile.domain.model

data class DailyInfoEntity(

    val excellentReviews: String,
    val badReviews: String,
    val highlights: List<ReviewEntity>,
    val topClasses: List<TopClassEntity>
)

data class TopClassEntity(

    val _class: ReviewClassEntity,
    val count: String
)