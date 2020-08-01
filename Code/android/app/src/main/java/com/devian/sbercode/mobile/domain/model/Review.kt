package com.devian.sbercode.mobile.domain.model

data class ReviewEntity(
    val id: String,
    val date: String,
    val app_id: String,
    val app_name: String,
    val rating: String,
    val text: String?,
    val _class: ReviewClassEntity
)