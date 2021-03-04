package com.icoffee.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @Name TokenDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-03-04 11:39
 */
@Data
@AllArgsConstructor
public class TokenDto {
    /**
     * 请求token
     */
    private String token;
    /**
     * 过期时间点
     */
    private Date expireAt;
}
