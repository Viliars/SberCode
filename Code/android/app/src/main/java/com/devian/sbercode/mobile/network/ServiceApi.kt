package com.devian.sbercode.mobile.network

import com.devian.sbercode.mobile.domain.model.LoginDataEntity
import com.devian.sbercode.mobile.network.model.*
import io.reactivex.Single
import retrofit2.http.*

interface ServiceApi {

    /* ----- GET ----- */

    @GET("/v1/reviews")
    fun getReviews(@Query(value = "lastId") lastId: String): Single<List<ApiReview>>

    @GET("/v1/classes")
    fun getClasses(): Single<List<ApiClass>>

    @GET("/v1/dailyInfo")
    fun getDailyInfo(): Single<ApiDailyInfo>


    /* ----- POST ----- */

    @POST("/v1/login")
    fun login(@Body loginData: LoginDataEntity): Single<ApiLoginResult>

    @POST("/v1/pushReviewError")
    fun pushReviewError(@Body wrongClass: ApiWrongClass): Single<ApiResponse>

}