package com.icoffee.security.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.UUID;

/**
 * @Name AuthErrorResponseBodyDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 15:12
 */
@Data
@JsonPropertyOrder({"uuid", "status", "code", "message"})
public class AuthErrorResponseBodyDto {

    private String uuid = UUID.randomUUID().toString().replace("-", "");
    private Integer status;
    private String code;
    private String message;

    public AuthErrorResponseBodyDto(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;

    }

    public String toJson() {
        return JSONObject.toJSONString(this);
    }

}
