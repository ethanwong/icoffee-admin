package com.icoffee.common.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * @Name BaseDomain
 * @Description 实体类基类
 * @Author chenly
 * @Create 2019-11-29 16:39
 */
@MappedSuperclass
@Data
public class BaseDomain implements Serializable {

    /**
     * ID
     */
    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");
    /**
     * 创建时间（时间戳）
     */
    private Long createAt = System.currentTimeMillis();
    /**
     * 更新时间（时间戳）
     */
    private Long updateAt;
}