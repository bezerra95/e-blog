package com.eprogramar.blog

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Controller
class SignUp {

    @RequestMapping("/signup", method = [RequestMethod.GET])
    fun form(): String {
        return "signup"
    }
}