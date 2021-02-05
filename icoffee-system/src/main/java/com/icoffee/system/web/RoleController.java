package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Role;
import com.icoffee.system.dto.ElTreeDto;
import com.icoffee.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = {"系统管理-角色API"})
@RestController
@RequestMapping("/api/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @AuthorizePoint(name = "新增角色", module = "role")
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping(value = "")
    public ResultDto create(HttpServletRequest request, @ModelAttribute("role") Role role) {
        return roleService.saveEntity(role);
    }

    @AuthorizePoint(name = "修改角色", module = "role")
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PutMapping(value = "")
    public ResultDto update(HttpServletRequest request, @ModelAttribute("role") Role role) {
        return roleService.updateEntity(role);
    }

    @AuthorizePoint(name = "根据ID查询角色", module = "role")
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询")
    @GetMapping(value = "/getById/{id}")
    public ResultDto getById(HttpServletRequest request, @PathVariable String id) {
        return ResultDto.returnSuccessData(roleService.getById(id));
    }

    @AuthorizePoint(name = "删除角色", module = "role")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping(value = "")
    public ResultDto delete(HttpServletRequest request, @RequestParam String id) {
        return roleService.deleteById(id);
    }

    @AuthorizePoint(name = "获取角色分页列表", module = "role")
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表")
    @GetMapping(value = "/page")
    public PageDto page(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        QueryWrapper<Role> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        return roleService.selectPage(queryWrapper, page, limit);
    }

    @AuthorizePoint(name = "获取角色授权信息", module = "role")
    @ApiOperation(value = "获取角色授权信息", notes = "获取角色授权信息")
    @GetMapping(value = "/auth/{roleId}")
    public List<ElTreeDto> auth(Model model, @PathVariable String roleId) {
        List<ElTreeDto> roleAuthList = roleService.getRoleAuth(roleId);
        return roleAuthList;
    }

    @AuthorizePoint(name = "设置角色授权信息", module = "role")
    @ApiOperation(value = "设置角色授权信息", notes = "设置角色授权信息")
    @PostMapping(value = "/setRoleAuth")
    public ResultDto setRoleAuth(@RequestParam("roleId") String roleId, @RequestParam("menuIds") List<String> menuIds, @RequestParam("authIds") List<String> authIds) throws IllegalAccessException {
        return roleService.setRoleAuth(roleId, menuIds, authIds);
    }

    @AuthorizePoint(name = "获取角色列表", module = "role")
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @GetMapping(value = "/getList")
    public ResultDto getList(HttpServletRequest request) {
        List<Role> roles = roleService.getBaseMapper().selectList(new QueryWrapper<Role>().ne("name", "SuperAdministrator"));
        return ResultDto.returnSuccessData(roles);
    }
}
