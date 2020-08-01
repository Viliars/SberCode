package com.devian.sbercode.mobile.network

import com.devian.sbercode.mobile.network.model.*
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class ServiceApiMock : ServiceApi {

    override fun getReviews(lastId: String): Single<List<ApiReview>> {
        val id = lastId.toInt()
        val res = mutableListOf<ApiReview>()
        if (id == 0) {
            for (i in 24 downTo 18) {
                res.add(ApiReview(i.toString(), "2019", "499999001", "Сбербанк Онлайн", "1", "Отзыв $i", apiClasses[0]))
            }
        } else {
            for (i in id-1 downTo id-7) {
                if (i>0) {
                    res.add(ApiReview(i.toString(), "2019", "499999001", "Сбербанк Онлайн", "1", "Отзыв $i", apiClasses[0]))
                }
            }
        }

        return Single.just(res.toList()).timeout(3000, TimeUnit.SECONDS)
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
            id = "1000",
            name = "Хлебный отдел"
        ),
        ApiClass(
            id = "1001",
            name = "Молочный отдел"
        ),
        ApiClass(
            id = "1002",
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
            "bla bla bla",
            apiClasses[1]
        ),
        ApiReview(
            "1",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "еще отзыв",
            apiClasses[0]
        ),
        ApiReview(
            "1",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Хороший отзыв",
            apiClasses[0]
        ),
        ApiReview(
            "1",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Плохой отзыв",
            apiClasses[0]
        ),
        ApiReview(
            "1",
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Отзыв номер пять",
            apiClasses[0]
        )
    )
}