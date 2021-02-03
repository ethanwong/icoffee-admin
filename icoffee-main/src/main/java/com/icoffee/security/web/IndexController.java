package com.icoffee.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Name IndexController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-02-02 11:40
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("")
    public String index(RedirectAttributes attributes) {
        attributes.addAttribute("access_token","");
        return "redirect:/swagger-ui/index.html";
    }
}
