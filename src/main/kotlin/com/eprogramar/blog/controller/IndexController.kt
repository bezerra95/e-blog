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
    private val categoryService: CategoryService, // Serviço para manipulação de categorias
    private val articleService: ArticleService // Serviço para manipulação de artigos
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass) // Logger para registrar informações de depuração

    @GetMapping
    fun index(
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int, // Parâmetro da página, padrão 0
        model: Model // Objeto de modelo para enviar dados ao template HTML
    ): String {
        logger.info("index()...") // Registra a chamada ao método

        // Obtém uma página de artigos usando o serviço de artigos.
        val articles = articleService.findAll(page)

        // Calcula o número da próxima página para navegação.
        val nextPage = if (page >= articles.totalElements - 1) page else page + 1
        // Calcula o número da página anterior para navegação.
        val priorPage = if (page <= 0) 0 else page - 1

        // Adiciona atributos ao modelo que serão usados no template HTML.
        model.addAttribute("categories", categoryService.findAll()) // Lista de categorias para exibição.
        model.addAttribute("articles", articles) // Lista de artigos para exibição.
        model.addAttribute("nextPage", nextPage) // Número da próxima página para navegação.
        model.addAttribute("priorPage", priorPage) // Número da página anterior para navegação.
        model.addAttribute("isFirst", articles.isFirst) // Verifica se a página atual é a primeira.
        model.addAttribute("isLast", articles.isLast) // Verifica se a página atual é a última.
        model.addAttribute("author", articles.content.get(0).author) // Autor do primeiro artigo na lista.

        // Retorna o nome do template HTML para renderização.
        return "index"
    }
}

