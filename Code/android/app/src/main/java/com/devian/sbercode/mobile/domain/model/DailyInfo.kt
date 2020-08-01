package com.devian.sbercode.mobile.domain.model

data class DailyInfoEntity(

    val excellentReviews: Int,
    val badReviews: Int,
    val highlights: List<ReviewEntity>,
    val topClasses: List<TopClassEntity>
)

data class TopClassEntity(

    val _class: ReviewClassEntity,
    val count: Int
)