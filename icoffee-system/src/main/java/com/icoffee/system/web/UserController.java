package com.icoffee.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SearchFilter;
import com.icoffee.system.domain.Role;
import com.icoffee.system.domain.User;
import com.icoffee.system.service.RoleService;
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
    @Autowired
    private RoleService roleService;

    @AuthorizePoint(name = "新增用户", module = "user")
    @PostMapping(value = "")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public ResultDto create(HttpServletRequest request, @RequestBody User user) {
        return userService.saveEntity(user);
    }

    @AuthorizePoint(name = "修改用户", module = "user")
    @PutMapping(value = "")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public ResultDto update(HttpServletRequest request, @RequestBody User user) {
        return userService.updateEntity(user);
    }

    @AuthorizePoint(name = "删除用户", module = "user")
    @DeleteMapping(value = "{ids}")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public ResultDto delete(HttpServletRequest request, @PathVariable String ids) throws Exception {
        ResultDto resultDto = new ResultDto();
        String[] userIds = ids.split(",");
        for(String userId:userIds){
            resultDto = userService.deleteById(userId);
            if (!resultDto.success) {
                return resultDto;
            }
        }
        return ResultDto.returnSuccess();
    }

    @AuthorizePoint(name = "根据ID获取用户", module = "user")
    @GetMapping(value = "/getById/{userId}")
    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    public ResultDto getById(HttpServletRequest request, @PathVariable String userId) {
        User user = userService.getById(userId);
        user.setPassword("");
        return ResultDto.returnSuccessData(user);
    }

    @AuthorizePoint(name = "根据用户名获取用户", module = "user")
    @GetMapping(value = "/getByUserName/{username}")
    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    public ResultDto getByUserName(HttpServletRequest request, @PathVariable String username) {
        User userDO = userService.getByUsername(username);
        if (userDO == null) {
            return ResultDto.returnFail("");
        }
        userDO.setPassword("");
        return ResultDto.returnSuccessData(userDO);
    }

    @AuthorizePoint(name = "用户分页", module = "user")
    @GetMapping(value = "/page")
    @ApiOperation(value = "用户分页", notes = "用户分页")
    public ResultDto page(HttpServletRequest request, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
        QueryWrapper<User> queryWrapper = SearchFilter.buildByHttpRequestList(request);
        queryWrapper.orderByDesc("create_at");
        PageDto<User> pageDto = userService.selectPage(queryWrapper, pageNo, pageSize);

        for(User user:pageDto.getItems()){
            if(StringUtils.isBlank(user.getRoleId())){
                continue;
            }
            Role role = roleService.getById(user.getRoleId());
            if(role ==null){
                continue;
            }
            user.setRoleName(role.getName());
            user.setPassword("");
        }

        return ResultDto.returnSuccessData(pageDto);
    }


    @AuthorizePoint(name = "修改密码", module = "user")
    @PutMapping(value = "resetPassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResultDto resetPassword(@RequestParam String username,@RequestParam String password){
        if(StringUtils.isBlank(username)){
            return ResultDto.returnFail("用户名称不能为空");
        }
        if(StringUtils.isBlank(password)){
            return ResultDto.returnFail("密码不能为空");
        }
        return userService.resetPassword(username,password);
    }

}