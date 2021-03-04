package com.icoffee.common.exception;

/**
 * @Name TokenAuthCode
 * @Description
 * @Author huangyingfeng
 * @Create 2021-03-03 15:33
 */
public enum TokenAuthCode {

    TokenExpired("token过期"),SignatureVerification("签名错误"),InvalidClaim("无效的Claim"),JWTVerification("校验异常");

    TokenAuthCode(String name){
        this.name = name;
    };

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
