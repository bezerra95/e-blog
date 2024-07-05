package com.eprogramar.blog

import com.eprogramar.blog.entity.Article
import com.eprogramar.blog.entity.Author
import com.eprogramar.blog.entity.Category
import com.eprogramar.blog.entity.User
import com.eprogramar.blog.repository.ArticleRepository
import com.eprogramar.blog.repository.AuthorRepository
import com.eprogramar.blog.repository.CategoryRepository
import com.eprogramar.blog.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DataLoader(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository
) : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        loadUser()
        loadCategories()
        loadArticles()
    }

    private fun loadCategories() {
        if (categoryRepository.count() == 0L) {
            listOf(
                    Category(name = "World"),
                    Category(name = "U.S."),
                    Category(name = "Technology"),
                    Category(name = "Design"),
                    Category(name = "Culture"),
                    Category(name = "Business"),
                    Category(name = "Politics"),
                    Category(name = "Opinion"),
                    Category(name = "Science"),
                    Category(name = "Health"),
                    Category(name = "Style"),
                    Category(name = "Travel")
            ).also { categoryRepository.saveAll(it) }
        }
    }

    private fun loadUser() {
        if (userRepository.count() == 0L) {
            val user = User(
                    name = "Administrator",
                    email = "admin@blog.com",
                    password = "admin"
            )
            userRepository.save(user).also { logger.info(it.toString()) }
        }
    }

    private fun loadArticles() {
        if (articleRepository.count() == 0L) {
            val user = userRepository.findAll().get(0)

            val author = authorRepository.save(
                Author(
                    user = user
                )
            )

            listOf(
                Article(
                    title = "ANÁLISE E DESENVOLVIMENTO DE SISTEMAS",

                    subTitle = "Modalidade: PRESENCIAL",

                    content = "Duração: 2 anos\n" +
                            "Endereço: UNIP - CAMPINAS - AV. COMENDADOR ENZO FERRARI," +
                            " 280 - SWIFT - CAMPINAS/SP - CEP: 13043900 -" +
                            " TEL. UNIDADE: (19) 3776-4000" +
                            " / TEL. POLO EAD: (19) 99756-1312 (19) 3776-4086",

                    date = LocalDateTime.now(),

                    author = author
                ),
                Article(
                    title = "Kotlin Web MVC com Spring Boot - Hands on",

                    subTitle = "Aprenda a programar Kotlin na Web através do " +
                            "desenvolvimento de uma aplicação real",

                    content = "Alguns minutos de estudo por dia valem a pena. " +
                            "Pesquisas mostram que os alunos que fazem do estudo " +
                            "um hábito têm maior probabilidade de alcançar suas metas.",

                    date = LocalDateTime.now(),

                    author = author
                )
            ).also { articleRepository.saveAll(it) }
        }
    }
}