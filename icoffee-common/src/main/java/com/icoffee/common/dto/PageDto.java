package com.icoffee.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Name PageDTO
 * @Description 分页信息DTO
 * @Author chenly
 * @Create 2019-12-10 13:41
 */
@Data
@NoArgsConstructor
public class PageDto<T> implements Serializable {

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 分页数据列表
     */
    private List<T> data;
}