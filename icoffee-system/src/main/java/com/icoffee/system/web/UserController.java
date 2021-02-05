package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.system.domain.User;
import com.icoffee.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name UserController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"系统管理-用户API"})
@RestController
@RequestMapping(value = "/api/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @AuthorizePoint(name = "新增用户", module = "user")
    @PostMapping(value = "")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public ResultDto create(HttpServletRequest request, @RequestBody User userDO) {
        return userService.saveEntity(userDO);
    }

    @AuthorizePoint(name = "修改用户", module = "user")
    @PutMapping(value = "")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public ResultDto update(HttpServletRequest request, @RequestBody User userDO) {
        return userService.updateEntity(userDO);
    }

    @AuthorizePoint(name = "删除用户", module = "user")
    @DeleteMapping(value = "")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public ResultDto delete(HttpServletRequest request, @RequestParam String id) throws Exception {
        return userService.deleteById(id);
    }

    @AuthorizePoint(name = "根据ID获取用户", module = "user")
    @GetMapping(value = "/getById/{userId}")
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public ResultDto getById(HttpServletRequest request, @PathVariable String userId) {
        return ResultDto.returnSuccessData(userService.getById(userId));
    }

    @AuthorizePoint(name = "根据用户名获取用户", module = "user")
    @GetMapping(value = "/getByUserName/{username}")
    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    public ResultDto getByUserName(HttpServletRequest request, @PathVariable String username) {
        User userDO = userService.getByUsername(username);
        if (userDO == null) {
            return ResultDto.returnFail("");
        }
        return ResultDto.returnSuccessData(userDO);
    }

    @AuthorizePoint(name = "用户分页", module = "user")
    @GetMapping(value = "/page")
    @ApiOperation(value = "用户分页", notes = "用户分页")
    public ResultDto page(HttpServletRequest request, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        PageDto<User> pageDTO = userService.selectPage(queryWrapper, pageNo, pageSize);
        return ResultDto.returnSuccessData(pageDTO);
    }


}