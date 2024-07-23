package com.eprogramar.blog.controller

import com.eprogramar.blog.service.ArticleService
import com.eprogramar.blog.service.CategoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/")
class IndexController(
    private val categoryService: CategoryService,
    private val articleService: ArticleService
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun index(
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        model:Model
    ): String {
        logger.info("index()...")

        val articles = articleService.findAll(page)
        val nextPage = if (page >= articles.totalElements-1) page else page + 1
        val priorPage = if (page <= 0) 0 else page - 1

        model.addAttribute("categories", categoryService.findAll())
        model.addAttribute("articles", articles)
        model.addAttribute("nextPage", nextPage)
        model.addAttribute("priorPage", priorPage)
        model.addAttribute("isFirst", articles.isFirst)
        model.addAttribute("isLast", articles.isLast)
        model.addAttribute("author", articles.content.get(0).author)
        return "index"
    }
}
