package com.icoffee.security.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name ErrorController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 11:43
 */
@Log4j2
@RestController
public class ErrorController {

    @GetMapping("error")
    public String error(){
        return "error";
    }

}
