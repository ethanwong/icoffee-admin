package com.icoffee.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

    public static final String SUCCESS_MESSAGE = "操作成功！";
    public static final String FAIL_MESSAGE = "操作失败！";

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功")
    public boolean success;
    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    public String message;
    /**
     * 数据域
     */
    @ApiModelProperty(value = "数据域")
    public Object data;

    public ResultDto(boolean success) {
        this.success = success;
        if (success) {
            this.message = SUCCESS_MESSAGE;
        } else {
            this.message = FAIL_MESSAGE;
        }
    }

    public ResultDto(boolean success, Object data) {
        this.success = success;
        if (success) {
            this.message = SUCCESS_MESSAGE;
        } else {
            this.message = FAIL_MESSAGE;
        }
        this.data = data;
    }

    /**
     * 操作成功响应
     *
     * @return
     */
    public static ResultDto returnSuccess() {
        return new ResultDto(true);
    }

    /**
     * 操作异常响应
     *
     * @param message
     * @return
     */
    public static ResultDto returnFail(String message) {
        return new ResultDto(false, message, null);
    }

    /**
     * 操作成功响应
     *
     * @param message
     * @return
     */
    public static ResultDto returnSuccess(String message) {
        return new ResultDto(true, message, null);
    }

    /**
     * 操作成功响应数据
     *
     * @param data
     * @return
     */
    public static ResultDto returnSuccessData(Object data) {
        return new ResultDto(true, data);
    }

    /**
     * 操作成功响应数据和信息
     * @param message
     * @param data
     * @return
     */
    public static ResultDto returnSuccessMessageData(String message, Object data) {
        return new ResultDto(true, message, data);
    }
}