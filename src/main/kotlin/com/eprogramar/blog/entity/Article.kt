package com.eprogramar.blog.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Article(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var title: String = "",
        var subTitle: String = "",
        var content: String  = "",
        var date: LocalDateTime = LocalDateTime.now(),

        @ManyToOne
        var author: Author = Author(),
        @ManyToOne
        var category: Category = Category()
)
