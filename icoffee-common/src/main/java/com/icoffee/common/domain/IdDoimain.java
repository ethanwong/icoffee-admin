package com.icoffee.common.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @Name IdDoimain
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 15:41
 */
@MappedSuperclass
@Data
public class IdDoimain {
    /**
     * ID
     */
    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");
}
