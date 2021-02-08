package com.icoffee.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name XTreeDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 14:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElTreeDto implements Serializable {
    private String id;
    private String name;
    private String module;
    private String parentId;
    private List<ElTreeDto> children = new ArrayList<>();
}
