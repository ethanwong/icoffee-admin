package com.icoffee.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Name PageDTO
 * @Description 分页信息DTO
 * @Author huangyingfeng
 * @Create 2019-12-10 13:41
 */
@Data
@NoArgsConstructor
public class PageDto<T> implements Serializable {

    /**
     * 当前页数
     */
    private Integer pageNo = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;

    /**
     * 总页数
     */
    private Long pages = 0L;

    /**
     * 总记录数
     */
    private Long total = 0L;

    /**
     * 分页数据列表
     */
    private List<T> items;
}