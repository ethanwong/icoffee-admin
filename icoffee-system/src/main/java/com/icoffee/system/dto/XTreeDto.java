package com.icoffee.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name XTreeDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 14:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XTreeDto implements Serializable {

    private String name;
    private String value;
    private String icon;
    private String iconSkin;
    private String code;
    private boolean nocheck;
    private boolean open = true;
    private boolean checked;
    private List<XTreeDto> children = new ArrayList<>();
    private Map<String, Object> map = new HashMap<>();

    private String type;//类型
}
