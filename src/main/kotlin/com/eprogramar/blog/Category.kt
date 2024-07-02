package com.eprogramar.blog

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Category(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var name: String = ""
)