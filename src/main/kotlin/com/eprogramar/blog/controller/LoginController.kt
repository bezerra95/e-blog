package com.eprogramar.blog.controller

import com.eprogramar.blog.entity.User
import com.eprogramar.blog.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession


@Controller
@RequestMapping("/login")
class LoginController(
    private val repository: UserRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("form()...")
        model.addAttribute("user", User())
        return "login"
    }

    @PostMapping
    fun login(user: User, model: Model, session: HttpSession): String {
        logger.info("Login($user)")

        val optional = repository.findByEmail(user.email)
        if (optional.isEmpty) {
            val messageError = "Usuário não localizado"
            logger.error(messageError)
            model.addAttribute("messageError", messageError)
            return "login"
        }

        val userDatabase = optional.get()
        if (user.password != userDatabase.password) {
            val messageError = "Senha inválida!"
            logger.error(messageError)
            model.addAttribute("messageError", messageError)
            return "login"
        }
        logger.info("Login executado com sucesso")
        session.setAttribute("currentUser", userDatabase)
        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/login"
    }
}