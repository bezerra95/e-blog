package com.eprogramar.blog

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader(
    private val userRepository: UserRepository
): CommandLineRunner {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        if (userRepository.count() == 0L) {
            val user = User(
                name = "Administrador" ,
                email = "admin@blog.com",
                password = "admin"
            )
            userRepository.save(user).also { user -> logger.info(user.toString()) }
        }
    }
}