package com.devian.sbercode.mobile.repository.network

import com.devian.sbercode.mobile.domain.model.DailyInfoEntity
import com.devian.sbercode.mobile.domain.model.ReviewClassEntity
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.domain.model.ReviewWrongClassEntity
import com.devian.sbercode.mobile.extensions.subscribeOnIo
import com.devian.sbercode.mobile.network.ServiceApi
import com.devian.sbercode.mobile.network.mapper.ApiDailyInfoMapper
import com.devian.sbercode.mobile.network.mapper.ApiReviewMapper
import com.devian.sbercode.mobile.network.model.ApiResponse
import io.reactivex.Single
import javax.inject.Inject

class ReviewsRepository @Inject constructor(
    private val serviceApi: ServiceApi,
    private val apiReviewMapper: ApiReviewMapper,
    private val apiDailyInfoMapper: ApiDailyInfoMapper
) {

    fun getReviews(): Single<List<ReviewEntity>> {
        return serviceApi.getReviews().map { it ->
            it.map { apiReviewMapper.transform(it) }
        }.subscribeOnIo()
    }

    fun getClasses(): Single<List<ReviewClassEntity>> {
        return serviceApi.getClasses().map { it ->
            it.map { apiReviewMapper.transform(it) }
        }
    }

    fun getDailyInfo(): Single<DailyInfoEntity> {
        return serviceApi.getDailyInfo().map { apiDailyInfoMapper.transform(it) }
    }

    fun pushReviewError(wrongClassEntity: ReviewWrongClassEntity): Single<ApiResponse> {
        return serviceApi.pushReviewError(apiReviewMapper.transform(wrongClassEntity))
    }
}