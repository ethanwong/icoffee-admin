package com.icoffee.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ElTreeDto> children;
    /**
     * 是否叶子节点，默认为否
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLeaf = false;
    /**
     * 扩展标识,用于识别当前的树节点信息是菜单信息还是授权信息
     * 如果是菜单信息，值为MENU
     * 如果是授权信息，值为AUTHORITY
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tag;

}
