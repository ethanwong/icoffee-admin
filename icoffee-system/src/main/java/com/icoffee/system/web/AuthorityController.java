package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Name AuthorityController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"鉴权API"})
@Controller
@RequestMapping("/api/system/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @ApiOperation(value = "批量创建授权", notes = "")
    @PostMapping(value = "/batchCreate")
    @ResponseBody
    public ResultDto batchCreate(@RequestParam String menuId) {
        return authorityService.batchSave(menuId);
    }

    @ApiOperation(value = "根据ID获取授权", notes = "")
    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public ResultDto getById(@PathVariable String id) {
        Authority authority = authorityService.getById(id);
        return ResultDto.returnSuccessData(authority);
    }

    @ApiOperation(value = "创建", notes = "")
    @PostMapping(value = "/create")
    @ResponseBody
    public ResultDto create(@ModelAttribute("authority") Authority authority) {
        String method = authority.getMethod();
        if (StringUtils.isNotBlank(method) &&
                !(method.equals("GET") || method.equals("POST") || method.equals("DELETE") || method.equals("PATCH") || method.equals("PUT"))) {
            return ResultDto.returnFail("方法参数错误!");
        }

        return authorityService.saveEntity(authority);
    }

    @ApiOperation(value = "更新", notes = "")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultDto update(@ModelAttribute("authority") Authority authority) {
        String method = authority.getMethod();
        if (StringUtils.isNotBlank(method) &&
                !(method.equals("GET") || method.equals("POST") || method.equals("DELETE") || method.equals("PATCH") || method.equals("PUT"))) {
            return ResultDto.returnFail("方法参数错误!");
        }

        return authorityService.updateEntity(authority);
    }

    @ApiOperation(value = "批量删除", notes = "")
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public ResultDto batchDelete(@RequestParam List<String> ids) {
        return authorityService.batchDelete(ids);
    }

    @ApiOperation(value = "获取分页列表", notes = "")
    @GetMapping(value = "/page")
    @ResponseBody
    public PageDto page(HttpServletRequest request, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        QueryWrapper<Authority> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        return authorityService.selectPage(queryWrapper, page, limit);
    }
}
