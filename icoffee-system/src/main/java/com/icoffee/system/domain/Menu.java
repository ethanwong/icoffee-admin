package com.icoffee.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.domain.BaseDomain;
import lombok.Data;

/**
 * @Name Menu
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-22 14:10
 */
@Data
@TableName("system_menu")
public class Menu extends BaseDomain {

    public String tableName;

    private String name;
}
