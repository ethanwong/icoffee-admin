package com.icoffee.oauth2.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDTO;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.oauth2.model.UserDO;
import com.icoffee.oauth2.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name UserController
 * @Description 用户管理控制器
 * @Author chenly
 * @Create 2019-12-02 17:01
 */
@Api(tags = {"用户API"})
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public ResultDto getById(HttpServletRequest request, @PathVariable String id) {
        return new ResultDto<>(ResultDto.SUCCESS, userService.getById(id));
    }

    @GetMapping(value = "/getByUserName/{username}")
    @ResponseBody
    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    public ResultDto getByUserName(HttpServletRequest request, @PathVariable String username) {
        UserDO userDO = userService.getByUsername(username);
        if(userDO == null){
            return ResultDto.failed();
        }
        return new ResultDto<>(ResultDto.SUCCESS, userDO);
    }

    @GetMapping(value = "/page")
    @ResponseBody
    @ApiOperation(value = "用户分页", notes = "用户分页")
    public ResultDto page(HttpServletRequest request, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.ne("username", "root");
        PageDTO<UserDO> pageDTO = userService.selectPage(queryWrapper, pageNo,pageSize);
        return new ResultDto<>(ResultDto.SUCCESS, pageDTO);
    }

    @PostMapping(value = "")
    @ResponseBody
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public ResultDto create(HttpServletRequest request, @RequestBody UserDO userDO){
        return userService.saveEntity(userDO);
    }

    @PutMapping(value = "")
    @ResponseBody
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public ResultDto update(HttpServletRequest request, @RequestBody UserDO userDO){
        return userService.updateEntity(userDO);
    }

    @DeleteMapping(value = "")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public ResultDto delete(HttpServletRequest request, @RequestParam String id) throws Exception {
        return userService.deleteById(id);
    }
}