package com.icoffee.common.dto;

import lombok.Data;

/**
 * @Name TableSchemaDto
 * @Description 表字段描述信息DTO
 * @Author huangyingfeng
 * @Create 2020-05-21 16:09
 */
@Data
public class TableSchemaDto {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段长度
     */
    private String columnLen;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 是否主键
     */
    private boolean primary;

    /**
     * 是否唯一
     */
    private boolean unique;

    /**
     * 是否非空
     */
    private boolean notNull;

    /**
     * 是否添加索引
     */
    private boolean index;

    public TableSchemaDto() {
    }

    public TableSchemaDto(String columnName, String columnType, String columnLen) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnLen = columnLen;
    }

    public TableSchemaDto(String columnName, String columnType, String columnLen, boolean index) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnLen = columnLen;
        this.index = index;
    }
}