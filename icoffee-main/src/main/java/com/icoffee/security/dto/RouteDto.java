package com.icoffee.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name RoutesDto
 * @Description 动态响应给前端的路由信息
 * @Author huangyingfeng
 * @Create 2021-01-27 14:36
 */
@Data
@AllArgsConstructor
public class RouteDto {

    /**
     * @param name      名称
     * @param path      路径
     * @param component 组件
     * @param meta      META
     * @param children  子路由
     */
    public RouteDto(String name, String path, String component, Map<String, Object> meta, List<RouteDto> children,Boolean hidden) {
        this.name = name;
        this.path = path;
        this.component = component;
        this.meta = meta;
        this.children = children;
        this.hidden = hidden;

    }

    /**
     * 名称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String name;
    /**
     * 隐藏
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean hidden;

    /**
     * 路径
     */
    private String path;
    /**
     * 组件
     */
    private String component;
    /**
     * 重定向组件路径
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String redirect;
    /**
     * meta信息
     */
    private Map<String, Object> meta = new HashMap<>();
    /**
     * 子级路由
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RouteDto> children = new ArrayList<>();
    /**
     * 角色
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> roles = new ArrayList<>();

}
