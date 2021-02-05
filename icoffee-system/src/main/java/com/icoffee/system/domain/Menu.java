package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Menu extends BaseDomain implements Serializable, Cloneable {
    /**
     * 名称
     */
    @NotBlank(message = "标题不能为空")
    private String title;
    /**
     * 图标
     */
    @NotBlank(message = "图标不能为空")
    private String icon;

    /**
     * 模块名称,Tag显示的时候用于区分模块
     */
    private String moduleName;

    /**
     * 路由地址
     */
    @NotBlank(message = "路由地址不能为空")
    private String routePath;

    /**
     * Vue组件路径,一级菜单的组件路径默认为 "Layout"
     */
    private String componentPath = "Layout";

    /**
     * 排序
     */
    private Integer orderNo = 1;

    /**
     * 是否可见
     */
    private Boolean hidden = true;


    /**
     * 是否跳转网址,默认为否
     */
    private Boolean redirect = false;
    /**
     * 上级ID
     */
    private String parentId = "0";

    /**
     * 子级菜单
     */
    @Transient
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<Menu>();
    /**
     * 父级菜单
     */
    @Transient
    @TableField(exist = false)
    private Menu parent;

    @Override
    public Menu clone() {
        Menu menu = null;
        try {
            menu = (Menu) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return menu;
    }
}
