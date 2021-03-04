package com.icoffee.security.dto;

/**
 * @Name TokenType
 * @Description
 * @Author huangyingfeng
 * @Create 2021-03-04 16:38
 */
public enum TokenType {

    ACCESS("ACCESS TOKEN"),REFRESH("REFRESH TOKEN");

    private String name;

    private TokenType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
