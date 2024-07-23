package com.eprogramar.blog.service

import com.eprogramar.blog.model.Category
import com.eprogramar.blog.repository.CategoryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun findAll(): List<Category> {
        return categoryRepository.findAll()
    }
}
