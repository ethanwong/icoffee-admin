package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.AuthorityDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.domain.User;
import com.icoffee.system.mapper.UserMapper;
import com.icoffee.system.service.AuthorityService;
import com.icoffee.system.service.RoleAuthorityService;
import com.icoffee.system.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name UserServiceImpl
 * @Description 用户模块Service接口实现类
 * @Author huangyingfeng
 * @Create 2019-12-02 11:06
 */
@Service
@Log4j2
public class UserServiceImpl extends MpBaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Autowired
    private AuthorityService authorityService;

    /**
     * 用户默认密码
     */
    @Value("${security.defaultPassword}")
    private String DEFAULTPASSWORD;


    @Override
    public User getByUsername(String username) {
        return getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @Override
    public ResultDto saveUser(User user) {
        try {
            if (existsUsername(user.getUsername())) {
                return ResultDto.returnFail("账号已存在");
            }
            if (StringUtils.isBlank(user.getPassword())) {
                user.setPassword(DEFAULTPASSWORD);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            save(user);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.returnFail("");
        }
    }

    @Override
    public ResultDto updateUser(User user) {
        try {
            String username = user.getUsername();
            User userDb = getByUsername(username);
            if (userDb != null && !user.getId().equals(userDb.getId())) {
                return ResultDto.returnFail("已经存在账号:" + username);
            }

            if (StringUtils.isBlank(user.getPassword())) {
                user.setPassword(userDb.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            user.setCreateAt(userDb.getCreateAt());
            user.setUpdateAt(System.currentTimeMillis());
            saveOrUpdate(user);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error(e);
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public List<User> getUserListByRoleId(String roleId) {
        return getBaseMapper().selectList(Wrappers.<User>lambdaQuery().eq(User::getRoleId, roleId));
    }

    @Override
    public ResultDto resetPassword(String username, String password) {
        try {
            User userDb = getByUsername(username);
            userDb.setPassword(passwordEncoder.encode(password));
            userDb.setUpdateAt(System.currentTimeMillis());
            saveOrUpdate(userDb);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error(e);
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public List<AuthorityDto> getUserGrantedAuthorities(String roleId) {
        List<String> authids = roleAuthorityService.getAuthIdsByRoleId(roleId);
        List<AuthorityDto> authorities = new ArrayList<>();
        for (String authid : authids) {
            Authority authority = authorityService.getById(authid);
            AuthorityDto authorityDto = new AuthorityDto(authority.getName(), authority.getUri(), authority.getMethod(), authority.getPermission(), authority.getModule());
            authorities.add(authorityDto);
        }
        return authorities;
    }


    /**
     * 根据用户名查询用户信息是否存在
     *
     * @param username
     * @return
     */
    public boolean existsUsername(String username) {
        User userDO = getBaseMapper().selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (userDO == null) {
            return false;
        }
        return true;
    }

}