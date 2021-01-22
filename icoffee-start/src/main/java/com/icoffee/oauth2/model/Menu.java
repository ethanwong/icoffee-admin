package com.icoffee.oauth2.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.icoffee.common.dto.BaseDO;
import lombok.Data;

/**
 * @Name Menu
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-22 14:10
 */
@Data
@TableName("system_menu")
public class Menu extends BaseDO {

    public String tableName;

    private String name;
}
