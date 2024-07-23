package com.eprogramar.blog.controller

import com.eprogramar.blog.model.Article
import com.eprogramar.blog.model.User
import com.eprogramar.blog.service.ArticleService
import com.eprogramar.blog.service.CategoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/article")
class ArticleController(
    private val categoryService: CategoryService,
    private val articleService: ArticleService
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("form()...")
        model.addAttribute("article", Article())
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @PostMapping
    fun save(article: Article, session: HttpSession, redirectAttributes: RedirectAttributes): String {
        logger.info("save($article)")

        val currentUser = session.getAttribute("currentUser") as User
        articleService.save(article, currentUser)

        val messageSuccess = "Artigo criado com sucesso!!!"
        redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess)

        logger.info(messageSuccess)

        return "redirect:/"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        model.addAttribute("articles", articleService.findAll())
        model.addAttribute("categories", categoryService.findAll())
        logger.info("list()...")
        return "article-list"
    }

    @GetMapping("/list/user/{userId}")
    fun listByAuthor(@PathVariable userId: Long, model: Model): String {
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByAuthorUserId(userId, sort))
        model.addAttribute("categories", categoryService.findAll())
        logger.info("list = ($userId)...")
        return "article-list"
    }

    @GetMapping("/list/category/{categoryId}")
    fun listByCategory(@PathVariable categoryId: Long, model: Model): String {
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByCategoryId(categoryId, sort))
        model.addAttribute("categories", categoryService.findAll())
        logger.info("category = ($categoryId)...")
        return "article-list"
    }

    @GetMapping("/edit/{articleId}")
    fun edit(@PathVariable articleId: Long, model: Model): String {
        logger.info("edit($articleId)...")
        model.addAttribute("article", articleService.findById(articleId))
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @GetMapping("/delete/{articleId}")
    fun delete(@PathVariable articleId: Long, model: Model): String {
        logger.info("delete($articleId)...")
        articleService.deleteById(articleId)
        return "redirect:/article/list"
    }
}
