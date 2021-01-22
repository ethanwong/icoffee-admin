package com.icoffee.oauth2.common;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Name ResultDTO
 * @Description 操作结果响应对象
 * @Author chenly
 * @Create 2019-12-02 11:25
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ResultDTO<T> implements Serializable {

    /**
     * 状态 0-成功，99-失败
     */
    private Integer code = 0;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据内容
     */
    private T data;

    /**
     * 成功状态
     */
    public static final int SUCCESS = 200;

    /**
     * 失败状态
     */
    public static final int FAILED = 99;

    public static ResultDTO success() {
        return new ResultDTO(SUCCESS);
    }

    public static ResultDTO failed() {
        return new ResultDTO(FAILED);
    }

    public static ResultDTO failed(String message) {
        return new ResultDTO(FAILED, message);
    }

    public static ResultDTO success(Object object) {
        ResultDTO resultDTO = new ResultDTO(SUCCESS);
        resultDTO.data = object;
        return resultDTO;
    }

    public ResultDTO(Integer status) {
        setCode(status);
    }

    public ResultDTO(Integer code, String message) {
        setCode(code);
        this.message = message;
    }

    public ResultDTO(Integer code, T obj) {
        setCode(code);
        this.data = obj;
    }

    public void setCode(Integer code) {
        this.code = code;
        if (code == SUCCESS) {
            this.message = "操作成功！";
        } else {
            this.message = "操作失败！";
        }
    }

    public static String resultJson(Integer code, String message) {
        if (code == null || StringUtils.isBlank(message)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    @JsonIgnore
    public boolean isFailed() {
        return FAILED == this.code;
    }
}