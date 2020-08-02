package com.devian.sbercode.mobile.network

import com.devian.sbercode.mobile.domain.model.LoginDataEntity
import com.devian.sbercode.mobile.network.model.*
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class ServiceApiMock : ServiceApi {

    override fun getReviews(lastId: String): Single<List<ApiReview>> {
        val id = lastId.toInt()
        val res = mutableListOf<ApiReview>()
        if (id == 0) {
            for (i in 24 downTo 18) {
                res.add(ApiReview(i, "2019", "499999001", "Сбербанк Онлайн", "1", "Отзыв $i", "1000"))
            }
        } else {
            for (i in id-1 downTo id-7) {
                if (i>0) {
                    res.add(ApiReview(i, "2019", "499999001", "Сбербанк Онлайн", "1", "Отзыв $i", "1000"))
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

    override fun login(loginData: LoginDataEntity): Single<ApiLoginResult> {
        return Single.just(
            ApiLoginResult(
                success = true,
                token = "3c1eb686-665f-4653-9224-72fe1ce6c8e6",
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
            _class = "1000",
            count = 8
        ),
        ApiTopClass(
            _class = "1001",
            count = 5
        )
    )

    private val apiReviews = listOf(
        ApiReview(
            1,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "bla bla",
            "1000"
        ),
        ApiReview(
            2,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "3",
            "bla bla bla",
            "1000"
        ),
        ApiReview(
            3,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "еще отзыв",
            "1000"
        ),
        ApiReview(
            4,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Хороший отзыв",
            "1000"
        ),
        ApiReview(
            5,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Плохой отзыв",
            "1000"
        ),
        ApiReview(
            6,
            "2019",
            "499999001",
            "Сбербанк Онлайн",
            "1",
            "Отзыв номер пять",
            "1000"
        )
    )
}