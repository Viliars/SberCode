package com.devian.sbercode.mobile.network.mapper

import com.devian.sbercode.mobile.domain.model.ReviewClassEntity
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.domain.model.ReviewWrongClassEntity
import com.devian.sbercode.mobile.network.model.ApiClass
import com.devian.sbercode.mobile.network.model.ApiReview
import com.devian.sbercode.mobile.network.model.ApiWrongClass
import javax.inject.Inject

class ApiReviewMapper @Inject constructor() {

    fun transform(apiReview: ApiReview): ReviewEntity {
        return ReviewEntity(
            id = apiReview.id,
            date = apiReview.date,
            app_id = apiReview.app_id,
            app_name = apiReview.app_name,
            rating = apiReview.rating,
            text = apiReview.text,
            _class = transform(apiReview._class)
        )
    }

    fun transform(reviewEntity: ReviewEntity): ApiReview {
        return ApiReview(
            id = reviewEntity.id,
            date = reviewEntity.date,
            app_id = reviewEntity.app_id,
            app_name = reviewEntity.app_name,
            rating = reviewEntity.rating,
            text = reviewEntity.text,
            _class = transform(reviewEntity._class)
        )
    }

    fun transform(apiClass: ApiClass): ReviewClassEntity {
        return ReviewClassEntity(
            id = apiClass.id,
            name = apiClass.name
        )
    }

    fun transform(reviewClassEntity: ReviewClassEntity): ApiClass {
        return ApiClass(
            id = reviewClassEntity.id,
            name = reviewClassEntity.name
        )
    }

    fun transform(apiWrongClass: ApiWrongClass): ReviewWrongClassEntity {
        return ReviewWrongClassEntity(
            review = transform(apiWrongClass.review),
            rightClassId = apiWrongClass.rightClassId
        )
    }

    fun transform(wrongClassEntity: ReviewWrongClassEntity): ApiWrongClass {
        return ApiWrongClass(
            review = transform(wrongClassEntity.review),
            rightClassId = wrongClassEntity.rightClassId
        )
    }
}