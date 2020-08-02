package com.devian.serviceapi.repository

import com.devian.serviceapi.domain.Review
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: CrudRepository<Review, Int> {
}