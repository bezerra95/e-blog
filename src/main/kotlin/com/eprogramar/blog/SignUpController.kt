package com.eprogramar.blog

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/signup")
class SignUpController {

    @GetMapping
    fun form(): String {
        return "signup"
    }

    @PostMapping
    fun save(user: User): String {
        println(user)
        return "redirect:/"
    }
}