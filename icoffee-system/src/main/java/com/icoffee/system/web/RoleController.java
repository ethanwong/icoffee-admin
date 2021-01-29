package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Role;
import com.icoffee.system.dto.XTreeDto;
import com.icoffee.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Name RoleController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"角色API"})
@Controller
@RequestMapping("/api/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping(value = "/create")
    @ResponseBody
    public ResultDto create(HttpServletRequest request, @ModelAttribute("role") Role role) {
        return roleService.saveEntity(role);
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PutMapping(value = "/update")
    @ResponseBody
    public ResultDto update(HttpServletRequest request, @ModelAttribute("role") Role role) {
        return roleService.updateEntity(role);
    }

    @ApiOperation(value = "根据ID查询", notes = "根据ID查询")
    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public ResultDto getById(HttpServletRequest request, @PathVariable String id) {
        return ResultDto.returnSuccessData(roleService.getById(id));
    }

    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResultDto delete(HttpServletRequest request, @RequestParam String id) {
        return roleService.deleteById(id);
    }

    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @GetMapping(value = "/page")
    @ResponseBody
    public PageDto page(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        QueryWrapper<Role> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        return roleService.selectPage(queryWrapper, page, limit);
    }

    @ApiOperation(value = "获取角色授权信息", notes = "获取角色授权信息")
    @GetMapping(value = "/auth/{roleId}")
    @ResponseBody
    public List<XTreeDto> auth(Model model, @PathVariable String roleId) {
        List<XTreeDto> roleAuthList = roleService.getRoleAuth(roleId);
        return roleAuthList;
    }

    @ApiOperation(value = "设置角色授权信息", notes = "设置角色授权信息")
    @PostMapping(value = "/setRoleAuth")
    @ResponseBody
    public ResultDto setRoleAuth(@RequestParam("roleId") String roleId, @RequestParam("menuIds") List<String> menuIds, @RequestParam("authIds") List<String> authIds) throws IllegalAccessException {
        return roleService.setRoleAuth(roleId, menuIds, authIds);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping(value = "/getList")
    @ResponseBody
    public ResultDto getList(HttpServletRequest request) {
        List<Role> roles = roleService.getBaseMapper().selectList(new QueryWrapper<Role>().ne("name", "SuperAdministrator"));
        return ResultDto.returnSuccessData(roles);
    }
}
