package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Name Authority
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 18:06
 */
@Data
@Entity
@Table(name = "system_authority")
@TableName(value = "system_authority")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Authority extends BaseDomain implements Serializable {
    /**
     * 授权名称
     */
    private String name;
    /**
     * 资源URI
     */
    private String uri;
    /**
     * 资源URI的方法
     */
    private String method;
    /**
     * 授权标签
     */
    private String permission;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 归属模块
     */
    private String module;


}
