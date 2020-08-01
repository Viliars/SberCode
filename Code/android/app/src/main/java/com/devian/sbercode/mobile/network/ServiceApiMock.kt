package com.devian.sbercode.mobile.network

import com.devian.sbercode.mobile.network.model.*
import io.reactivex.Single

class ServiceApiMock : ServiceApi {

    override fun getReviews(): Single<List<ApiReview>> {
        return Single.just(apiReviews)
    }

    override fun getClasses(): Single<List<ApiClass>> {
        return Single.just(apiClasses)
    }

    override fun getDailyInfo(): Single<ApiDailyInfo> {
        return Single.just(
            ApiDailyInfo(
                excellentReviews = "48",
                badReviews = "15",
                highlights = apiReviews,
                topClasses = apiTopClasses
            )
        )
    }

    override fun login(loginData: ApiLoginData): Single<ApiLoginResult> {
        return Single.just(
            ApiLoginResult(
                success = true,
                token = "65ds4sdf564sdf854sdf846",
                error = ""
            )
        )
    }

    override fun pushReviewError(wrongClass: ApiWrongClass): Single<ApiResponse> {
        return Single.just(ApiResponse(success = true))
    }

    private val apiClasses = listOf(
        ApiClass(
            id = "1",
            name = "Хлебный отдел"
        ),
        ApiClass(
            id = "2",
            name = "Молочный отдел"
        ),
        ApiClass(
            id = "3",
            name = "Мясной отдел"
        )
    )

    private val apiTopClasses = listOf(
        ApiTopClass(
            _class = apiClasses[0],
            count = "8"
        ),
        ApiTopClass(
            _class = apiClasses[2],
            count = "5"
        )
    )

    private val apiReviews = listOf(
        ApiReview(
            "1",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "bla bla",
            apiClasses[0]
        ),
        ApiReview(
            "2",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "3",
            "bla bla",
            apiClasses[1]
        )
    )
}