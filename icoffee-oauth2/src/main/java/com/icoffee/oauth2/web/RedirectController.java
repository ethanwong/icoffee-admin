package com.icoffee.oauth2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name RedirectController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-28 15:01
 */
@RestController
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect";
    }
}
