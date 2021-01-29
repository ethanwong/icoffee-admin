package com.icoffee.system.web;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.dto.XTreeDto;
import com.icoffee.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Name MenuController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"系统管理-菜单API"})
@Controller
@RequestMapping("/api/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取菜单树", notes = "")
    @GetMapping(value = "/getTree")
    @ResponseBody
    public List<XTreeDto> getTree() {
        List<XTreeDto> menuList = menuService.getTree();
        return menuList;
    }

    @ApiOperation(value = "创建", notes = "")
    @PostMapping(value = "")
    @ResponseBody
    public ResultDto create(@ModelAttribute("menu") Menu menu) {
        return menuService.saveEntity(menu);
    }

    @ApiOperation(value = "更新", notes = "")
    @PutMapping(value = "")
    @ResponseBody
    public ResultDto update(@ModelAttribute("menu") Menu menu) {
        return menuService.updateEntity(menu);
    }

    @ApiOperation(value = "根据ID获取", notes = "")
    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public ResultDto getById(@PathVariable String id) {
        Menu menu = menuService.getById(id);
        return ResultDto.returnSuccessData(menu);
    }

    @ApiOperation(value = "删除", notes = "")
    @DeleteMapping(value = "")
    @ResponseBody
    public ResultDto delete(HttpServletRequest request, @RequestParam String id) {
        return menuService.delete(id);
    }

    @ApiOperation(value = "获取列表", notes = "")
    @GetMapping(value = "/list")
    @ResponseBody
    public ResultDto getMenu() {
        return menuService.getMenu();
    }

    @ApiOperation(value = "获取名称层级", notes = "")
    @PostMapping(value = "/getHierarchy")
    @ResponseBody
    public ResultDto getMenu(HttpServletRequest request) {
        String uri = request.getParameter("uri");
        return menuService.getNameHierarchy(uri);
    }
}
