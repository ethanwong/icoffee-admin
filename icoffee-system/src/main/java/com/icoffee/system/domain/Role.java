package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name Role
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 18:06
 */
@Data
@Entity
@Table(name = "system_role")
@TableName(value = "system_role")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseDomain implements Serializable {

    private String name;
    private String description;
    /**
     * 菜单ID列表
     */
    @Column(columnDefinition="TEXT")
    private String menuIds;
    /**
     * 授权ID列表
     */
    @Column(columnDefinition="TEXT")
    private String authIds;


    @Transient
    @TableField(exist = false)
    private List<Menu> menus = new ArrayList<>();

    @Transient
    @TableField(exist = false)
    private List<Authority> auths = new ArrayList<>();


}
