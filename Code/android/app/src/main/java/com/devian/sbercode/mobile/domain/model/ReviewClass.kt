package com.devian.sbercode.mobile.domain.model

import com.devian.sbercode.mobile.network.model.ApiReview
import com.google.gson.annotations.SerializedName

class ReviewClassEntity(
    val id: String,
    val name: String
)

class ReviewWrongClassEntity(
    val review: ReviewEntity,
    val rightClassId: String
)