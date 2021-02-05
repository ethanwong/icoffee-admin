package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        QueryWrapper<Authority> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        PageDto<Authority> pageDTO =  authorityService.selectPage(queryWrapper, pageNo, pageSize);
        return ResultDto.returnSuccessData(pageDTO);
    }
}
