package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.dto.ElTreeDto;
import com.icoffee.system.service.AuthorityService;
import com.icoffee.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name AuthorityController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"系统管理-鉴权API"})
@RestController
@RequestMapping("/api/system/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private MenuService menuService;

    @AuthorizePoint(name = "根据ID获取授权", module = "authority")
    @ApiOperation(value = "根据ID获取授权", notes = "")
    @GetMapping(value = "/getById/{id}")
    public ResultDto getById(@PathVariable String id) {
        Authority authority = authorityService.getById(id);
        return ResultDto.returnSuccessData(authority);
    }

    @AuthorizePoint(name = "获取授权分页列表", module = "authority")
    @ApiOperation(value = "获取分页列表", notes = "")
    @GetMapping(value = "/page")
    public ResultDto page(HttpServletRequest request, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        QueryWrapper<Authority> queryWrapper = new QueryWrapper<>();
        String moduleName = request.getParameter("search_EQ_module");
        String name = request.getParameter("search_LIKE_name");


        if (StringUtils.isNotBlank(moduleName)) {
            Menu menu = menuService.getMenuByModuleName(moduleName);
            List<String> modules = new ArrayList<>();
            for (Menu child : menu.getChildren()) {
                modules.add(child.getModuleName());
            }
            modules.add(moduleName);
            queryWrapper.in("module", modules);
        }

        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("create_at");
        PageDto<Authority> pageDTO = authorityService.selectPage(queryWrapper, pageNo, pageSize);
        return ResultDto.returnSuccessData(pageDTO);
    }

    @AuthorizePoint(name = "根据模块获取授权", module = "authority")
    @ApiOperation(value = "根据模块获取授权", notes = "")
    @GetMapping(value = "/getByModule/{module}")
    public ResultDto getByModule(@PathVariable String module) {

        Menu menu = menuService.getMenuByModuleName(module);

        List<Authority> authoritys = authorityService.getByModule(module);

        List<ElTreeDto> elTreeDtoList = new ArrayList<>();
        for (Authority authority : authoritys) {
            ElTreeDto elTreeDto = new ElTreeDto();
            elTreeDto.setId(authority.getId());
            elTreeDto.setName(authority.getName());
            elTreeDto.setModule(module);
            elTreeDto.setParentId(menu.getId());
            elTreeDto.setTag("AUTHORITY");
            elTreeDto.setIsLeaf(true);
            elTreeDtoList.add(elTreeDto);
        }

        return ResultDto.returnSuccessData(elTreeDtoList);
    }

}
