package com.devian.serviceapi.controllers

import com.devian.serviceapi.domain.*
import com.devian.serviceapi.repository.Database
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1")
class ApiController {

    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var database: Database

    /* ----- GET ----- */

    @GetMapping("/classes")
    fun getClasses(@RequestHeader("Authorization") token: String): String {
        val list = database.reviewClassRepository.findAll()
        return gson.toJson(list)
    }

    @GetMapping("/reviews")
    fun getReviews(@RequestHeader("Authorization") token: String,
                   @RequestParam("lastId") lastId: String): String {
        val id = lastId.toInt()

        val classId = database.userRepository.findByToken(token).get().classId
        val allReviews = database.reviewRepository.findAll()
                .filter { r -> r.clazz == classId }
                .sortedByDescending { it.id }

        if (id != 0) {
            allReviews.filter { r -> r.id < id }
        }

        return gson.toJson(allReviews.take(10))
    }

    @GetMapping("/dailyInfo")
    fun getDailyInfo(@RequestHeader("Authorization") token: String): String {

        val list = database.reviewRepository.findAll().take(100)
        val classes = database.reviewClassRepository.findAll()
        val excellent = list.filter { r -> r.rating == "5" }.size
        val badReviews = list.filter { r -> r.rating == "2" || r.rating == "1" }.size
        val highlights = mutableListOf<Review>()
        if (list.size > 3) {
            highlights.add(list[(list.indices).random()])
            highlights.add(list[(list.indices).random()])
            highlights.add(list[(list.indices).random()])
        }
        val topList = HashMap<String, Int>()
        for (r in list) {
            for (c in classes) {
                if (c.id == r.clazz) {
                    topList[c.id] = if (topList[c.id] == null) 1 else topList[c.id]!! + 1
                }
            }
        }
        val topClasses = topList.toList().sortedByDescending { (_, value) -> value }.take(3)
        var resTopClasses: List<TopClassEntity> = listOf()
        if (topClasses.size >= 3) {
            resTopClasses = listOf(
                    TopClassEntity(clazz = topClasses[0].first, count = topClasses[0].second),
                    TopClassEntity(clazz = topClasses[1].first, count = topClasses[1].second),
                    TopClassEntity(clazz = topClasses[2].first, count = topClasses[2].second)
            )
        }

        return gson.toJson(DailyInfoEntity(
                excellentReviews = excellent.toString(),
                badReviews = badReviews.toString(),
                highlights = highlights,
                topClasses = resTopClasses
        ))
    }

    /* ----- POST ----- */

    @PostMapping("/login")
    fun login(@RequestBody loginData: String): String {
        val data = gson.fromJson<LoginDataEntity>(loginData, LoginDataEntity::class.java)
        val user = database.userRepository.findByOrganizationAndLoginAndPassHash(data.organization, data.login, data.passHash)
        if (user.isPresent) {
            return gson.toJson(LoginResultEntity(true, user.get().token))
        }
        return gson.toJson(LoginResultEntity(false, error = "Пользователь не найден"))
    }

    @PostMapping("/pushReviewError")
    fun pushReviewError(@RequestHeader("Authorization") token: String,
                        @RequestBody wrongClass: String): String {
        val data = gson.fromJson<ReviewWrongClass>(wrongClass, ReviewWrongClass::class.java)
        val optionalReview = database.reviewRepository.findById(data.reviewId)
        if (optionalReview.isPresent) {
            val review = optionalReview.get()
            val optionalClass = database.reviewClassRepository.findById(data.rightClassId)
            if (optionalClass.isPresent) {
                review.clazz = optionalClass.get().id
                database.reviewRepository.save(review)
                // todo передать на ML сервер
                return gson.toJson(ApiResponse(success = true))
            }
        }
        return gson.toJson(ApiResponse(success = false))
    }
}