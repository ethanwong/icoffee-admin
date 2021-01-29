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
 * @Name Menu
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 18:06
 */
@Data
@Entity
@Table(name = "system_menu")
@TableName(value = "system_menu")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Menu extends BaseDomain implements Serializable {

    private String name;
    private String uri;
    private String module;
    private String icon;
    private int orderNo;
    private String parentId = "0";
    private Integer isJump = 0;//是否跳转网址 0-否，1-是
}
