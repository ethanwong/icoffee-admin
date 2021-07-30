package com.icoffee.common.exception;

/**
 * @Name BusinessException
 * @Description 业务异常类
 * @Author huangyingfeng
 * @Create 2018-12-12 09:08
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String code = "-1";
	private String message;

	public BusinessException(String msg){
		this.message = msg;
	}

	public BusinessException(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public BusinessException(String code, String msg, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = msg;
    }

	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}