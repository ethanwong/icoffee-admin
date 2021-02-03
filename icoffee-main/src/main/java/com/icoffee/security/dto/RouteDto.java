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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private String path;
    private String component;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String redirect;
    private Map<String,Object> meta = new HashMap<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RouteDto> children = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> roles = new ArrayList<>();

}
