package com.devian.serviceapi.domain

import javax.persistence.*

@Entity
@Table(name = "review_class")
class ReviewClass(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: String,
        val name: String
)

class ReviewWrongClass(
        val reviewId: Int,
        val rightClassId: String
)