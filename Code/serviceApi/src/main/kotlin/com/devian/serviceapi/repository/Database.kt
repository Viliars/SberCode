package com.devian.serviceapi.repository

import org.springframework.stereotype.Component

@Component
class Database(
        val userRepository: UserRepository,
        val reviewRepository: ReviewRepository,
        val reviewClassRepository: ReviewClassRepository
)