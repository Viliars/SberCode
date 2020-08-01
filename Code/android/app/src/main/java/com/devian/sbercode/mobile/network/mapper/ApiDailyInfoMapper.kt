package com.devian.sbercode.mobile.network.mapper

import com.devian.sbercode.mobile.domain.model.DailyInfoEntity
import com.devian.sbercode.mobile.domain.model.TopClassEntity
import com.devian.sbercode.mobile.network.model.ApiDailyInfo
import com.devian.sbercode.mobile.network.model.ApiTopClass
import javax.inject.Inject

class ApiDailyInfoMapper @Inject constructor(
    private val apiReviewMapper: ApiReviewMapper
) {

    fun transform(apiDailyInfo: ApiDailyInfo): DailyInfoEntity {
        return DailyInfoEntity(
            excellentReviews = apiDailyInfo.excellentReviews,
            badReviews = apiDailyInfo.badReviews,
            highlights = apiDailyInfo.highlights.map {
                apiReviewMapper.transform(it)
            },
            topClasses = apiDailyInfo.topClasses.map {
                transform(it)
            }
        )
    }

    fun transform(apiTopClass: ApiTopClass): TopClassEntity {
        return TopClassEntity(
            _class = apiReviewMapper.transform(apiTopClass._class),
            count = apiTopClass.count
        )
    }

    fun transform(topClassEntity: TopClassEntity): ApiTopClass {
        return ApiTopClass(
            _class = apiReviewMapper.transform(topClassEntity._class),
            count = topClassEntity.count
        )
    }


}