package com.devian.serviceapi.repository

import com.devian.serviceapi.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, String> {
    fun findByToken(token: String): Optional<User>
    fun findByOrganizationAndLoginAndPassHash(org: String, login: String, passHash:String): Optional<User>
}