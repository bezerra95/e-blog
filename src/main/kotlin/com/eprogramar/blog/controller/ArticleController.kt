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
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpSession

@Controller // Indica que esta classe é um controlador Spring MVC
@RequestMapping("/article") // Mapeia todas as requisições que começam com "/article" para este controlador
class ArticleController(
    private val categoryService: CategoryService, // Injeção de dependência do serviço de categorias
    private val articleService: ArticleService // Injeção de dependência do serviço de artigos
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass) // Logger para registrar mensagens de log

    @GetMapping // Mapeia requisições GET para o método form()
    fun form(model: Model): String {
        logger.info("form()...") // Loga a chamada do método
        model.addAttribute("article", Article()) // Adiciona um novo objeto Article ao modelo
        model.addAttribute("categories", categoryService.findAll()) // Adiciona todas as categorias ao modelo
        return "article" // Retorna o nome do template de artigo (article.html)
    }

    @PostMapping // Mapeia requisições POST para o método save()
    fun save(article: Article, session: HttpSession, redirectAttributes: RedirectAttributes): String {
        logger.info("save($article)") // Loga a tentativa de salvar o artigo

        val currentUser = session.getAttribute("currentUser") as User // Obtém o usuário atual da sessão
        articleService.save(article, currentUser) // Salva o artigo associado ao usuário atual

        val messageSuccess = "Artigo criado com sucesso!!!" // Mensagem de sucesso
        redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess) // Adiciona a mensagem de sucesso aos atributos de redirecionamento

        logger.info(messageSuccess) // Loga a mensagem de sucesso

        return "redirect:/" // Redireciona para a página inicial
    }

    @GetMapping("/list") // Mapeia requisições GET para o método list()
    fun list(model: Model): String {
        model.addAttribute("articles", articleService.findAll()) // Adiciona todos os artigos ao modelo
        model.addAttribute("categories", categoryService.findAll()) // Adiciona todas as categorias ao modelo
        logger.info("list()...") // Loga a chamada do método
        return "article-list" // Retorna o nome do template de lista de artigos (article-list.html)
    }

    @GetMapping("/list/user/{userId}") // Mapeia requisições GET com um parâmetro userId para o método listByAuthor()
    fun listByAuthor(@PathVariable userId: Long, model: Model): String {
        val sort = Sort.by(Sort.Direction.DESC, "id") // Ordena os artigos por ID de forma decrescente
        model.addAttribute("articles", articleService.findByAuthorUserId(userId, sort)) // Adiciona os artigos do autor específico ao modelo
        model.addAttribute("categories", categoryService.findAll()) // Adiciona todas as categorias ao modelo
        logger.info("list = ($userId)...") // Loga a chamada do método com o userId
        return "article-list" // Retorna o nome do template de lista de artigos (article-list.html)
    }

    @GetMapping("/list/category/{categoryId}") // Mapeia requisições GET com um parâmetro categoryId para o método listByCategory()
    fun listByCategory(@PathVariable categoryId: Long, model: Model): String {
        val sort = Sort.by(Sort.Direction.DESC, "id") // Ordena os artigos por ID de forma decrescente
        model.addAttribute("articles", articleService.findByCategoryId(categoryId, sort)) // Adiciona os artigos da categoria específica ao modelo
        model.addAttribute("categories", categoryService.findAll()) // Adiciona todas as categorias ao modelo
        logger.info("category = ($categoryId)...") // Loga a chamada do método com o categoryId
        return "article-list" // Retorna o nome do template de lista de artigos (article-list.html)
    }

    @GetMapping("/edit/{articleId}") // Mapeia requisições GET com um parâmetro articleId para o método edit()
    fun edit(@PathVariable articleId: Long, model: Model): String {
        logger.info("edit($articleId)...") // Loga a chamada do método com o articleId
        model.addAttribute("article", articleService.findById(articleId)) // Adiciona o artigo específico ao modelo
        model.addAttribute("categories", categoryService.findAll()) // Adiciona todas as categorias ao modelo
        return "article" // Retorna o nome do template de artigo (article.html)
    }

    @GetMapping("/delete/{articleId}") // Mapeia requisições GET com um parâmetro articleId para o método delete()
    fun delete(@PathVariable articleId: Long, model: Model): String {
        logger.info("delete($articleId)...") // Loga a chamada do método com o articleId
        articleService.deleteById(articleId) // Deleta o artigo específico
        return "redirect:/article/list" // Redireciona para a lista de artigos
    }
}

