package com.icoffee.system.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name MenuDto
 * @Description 菜单列表
 * @Author lincy
 * @Create 2018-12-17 10:05
 */
@Data
public class MenuDto {

    private String title;
    private String name;
    private String icon;
    private String jump;
    private List<MenuDto> list = new ArrayList<>();

}
