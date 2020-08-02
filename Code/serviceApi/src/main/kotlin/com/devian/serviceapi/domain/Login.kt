package com.devian.serviceapi.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class LoginResultEntity(
        val success: Boolean,
        val token: String = "",
        val error: String = ""
)

data class LoginDataEntity(

        val organization:  String,
        val login: String,
        val passHash: String
)

@Entity
@Table(name = "users")
data class User(
        @Id
        val token: String,
        val organization:  String,
        val login: String,
        val passHash: String,
        val classId: String
)