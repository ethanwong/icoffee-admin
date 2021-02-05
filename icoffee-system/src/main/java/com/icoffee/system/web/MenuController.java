package com.icoffee.system.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name MenuController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"系统管理-菜单API"})
@RestController
@RequestMapping("/api/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @AuthorizePoint(name = "菜单分页", module = "menu")
    @GetMapping(value = "/page")
    @ApiOperation(value = "菜单分页", notes = "菜单分页")
    public ResultDto page(HttpServletRequest request, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        PageDto<Menu> pageDTO = menuService.findPage(pageNo, pageSize);
        return ResultDto.returnSuccessData(pageDTO);
    }

    @AuthorizePoint(name = "菜单分页", module = "menu")
    @ApiOperation(value = "创建", notes = "")
    @PostMapping(value = "")
    public ResultDto create(@Validated @RequestBody Menu menu) {
        return menuService.saveEntity(menu);
    }

    @AuthorizePoint(name = "更新菜单", module = "menu")
    @ApiOperation(value = "更新", notes = "")
    @PutMapping(value = "")
    public ResultDto update(@Validated @RequestBody  Menu menu) {
        return menuService.updateEntity(menu);
    }

    @AuthorizePoint(name = "删除菜单", module = "menu")
    @ApiOperation(value = "删除", notes = "")
    @DeleteMapping(value = "{id}")
    public ResultDto delete(HttpServletRequest request, @PathVariable String id) {
        return menuService.delete(id);
    }

    @AuthorizePoint(name = "菜单获取列表", module = "menu")
    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping(value = "/list")
    public ResultDto getMenuList(HttpServletRequest request) {
        QueryWrapper<Menu> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        return ResultDto.returnSuccessData(menuService.getRootMenuList(queryWrapper));
    }

    @AuthorizePoint(name = "根据ID获取菜单", module = "menu")
    @ApiOperation(value = "根据ID获取", notes = "")
    @GetMapping(value = "/getById/{id}")
    public ResultDto getById(@PathVariable String id) {
        Menu menu = menuService.getById(id);
        return ResultDto.returnSuccessData(menu);
    }

    @AuthorizePoint(name = "获取菜单树", module = "menu")
    @ApiOperation(value = "获取菜单树", notes = "")
    @GetMapping(value = "/getTree")
    public ResultDto getTree() {
        return menuService.getTree();

    }



}
