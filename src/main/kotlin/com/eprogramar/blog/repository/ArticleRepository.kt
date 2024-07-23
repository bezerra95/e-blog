package com.eprogramar.blog.repository

import com.eprogramar.blog.model.Article
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: JpaRepository<Article, Long> {
    fun findByAuthorUserId(userId: Long, sort: Sort): List<Article>
    fun findByCategoryId(categoryId: Long, sort: Sort): List<Article>
}