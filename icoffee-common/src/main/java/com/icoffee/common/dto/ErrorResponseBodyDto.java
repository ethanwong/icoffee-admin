package com.icoffee.common.dto;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @Name ErrorResponseBodyDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 15:12
 */
@Data
@JsonPropertyOrder({"uuid", "status", "code", "message"})
public class ErrorResponseBodyDto {

    private String uuid = UUID.randomUUID().toString().replace("-", "");
    private Integer status;
    private String code;
    private String message;

    public ErrorResponseBodyDto(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;

    }

    public String toJson() {
        return JSONObject.toJSONString(this);
    }

}
