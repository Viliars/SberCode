package com.devian.serviceapi.domain

import com.google.gson.annotations.SerializedName
import javax.persistence.*

@Entity
@Table(name = "reviews")
data class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int,
        val date: String,
        val app_id: String,
        val app_name: String,
        val rating: String,
        val text: String?,

        @SerializedName("class")
        var clazz: String
)