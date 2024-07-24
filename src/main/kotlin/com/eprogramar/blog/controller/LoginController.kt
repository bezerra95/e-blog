package com.eprogramar.blog.controller

import com.eprogramar.blog.model.User
import com.eprogramar.blog.repository.UserRepository
import com.eprogramar.blog.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller // Indica que esta classe é um controlador do Spring MVC
@RequestMapping("/login") // Mapeia todas as requisições que começam com "/login" para este controlador
class LoginController(
    private val userService: UserService // Injeção de dependência do serviço de usuário
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass) // Logger para registrar mensagens de log

    @GetMapping // Mapeia requisições GET para o método form()
    fun form(model: Model): String {
        logger.info("form()...") // Loga a chamada do método
        model.addAttribute("user", User()) // Adiciona um novo objeto User ao modelo
        return "login" // Retorna o nome do template de login (login.html)
    }

    @PostMapping // Mapeia requisições POST para o método login()
    fun login(user: User, model: Model, session: HttpSession): String {
        logger.info("Login($user)") // Loga a tentativa de login com os dados do usuário

        val optional = userService.findByEmail(user.email) // Busca o usuário pelo email
        if (optional.isEmpty) { // Verifica se o usuário não foi encontrado
            val messageError = "Usuário não localizado" // Mensagem de erro
            logger.error(messageError) // Loga o erro
            model.addAttribute("messageError", messageError) // Adiciona a mensagem de erro ao modelo
            return "login" // Retorna o template de login
        }

        val userDatabase = optional.get() // Obtém o usuário encontrado
        if (user.password != userDatabase.password) { // Verifica se a senha não corresponde
            val messageError = "Senha inválida!" // Mensagem de erro
            logger.error(messageError) // Loga o erro
            model.addAttribute("messageError", messageError) // Adiciona a mensagem de erro ao modelo
            return "login" // Retorna o template de login
        }
        logger.info("Login executado com sucesso") // Loga o sucesso do login
        session.setAttribute("currentUser", userDatabase) // Adiciona o usuário à sessão
        return "redirect:/" // Redireciona para a página inicial
    }

    @GetMapping("/logout") // Mapeia requisições GET para o método logout()
    fun logout(session: HttpSession): String {
        session.invalidate() // Invalida a sessão do usuário
        return "redirect:/login" // Redireciona para a página de login
    }
}
