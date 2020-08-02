package com.devian.serviceapi.repository

import com.devian.serviceapi.domain.ReviewClass
import org.springframework.data.repository.CrudRepository

interface ReviewClassRepository: CrudRepository<ReviewClass, String> {

}