package com.icoffee.oauth2.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.oauth2.service.MpBaseServiceImpl;
import com.icoffee.common.dto.ResultDTO;
import com.icoffee.oauth2.mapper.UserMapper;
import com.icoffee.oauth2.model.UserDO;
import com.icoffee.oauth2.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Name UserServiceImpl
 * @Description 用户模块Service接口实现类
 * @Author chenly
 * @Create 2019-12-02 11:06
 */
@Service
@Log4j2
public class UserServiceImpl extends MpBaseServiceImpl<UserMapper, UserDO> implements UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

//    @Autowired
//    private UserRoleService userRoleService;

    @Override
    public UserDO getByUsername(String username) {
        return getBaseMapper().selectOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username));
    }

    @Override
    public ResultDTO saveEntity(UserDO userDO) {
        try {
            if (existsUsername(userDO.getUsername())) {
                return ResultDTO.failed("账号已存在");
            }
            if (StringUtils.isBlank(userDO.getPassword())) {
                userDO.setPassword("123456");
            }
            userDO.setPassword(bCryptPasswordEncoder.encode(userDO.getPassword()));
//            userDO.setAreaId(areaId.replace(",",""));
            save(userDO);
//            userRoleService.save(new UserRoleDO(userDO.getId(), roleId));
            return ResultDTO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.failed();
        }
    }

    @Override
    public ResultDTO updateEntity(UserDO userDO) {
        try {
            if (existsUsername(userDO.getUsername())) {
                UserDO checkBean = getByUsername(userDO.getUsername());
                if (!userDO.getId().equals(checkBean.getId())) {
                    return ResultDTO.failed("账号重复");
                }
            }

            UserDO olduserDO = getById(userDO.getId());
            if (StringUtils.isBlank(userDO.getPassword())) {
                userDO.setPassword(olduserDO.getPassword());
            } else {
                userDO.setPassword(bCryptPasswordEncoder.encode(userDO.getPassword()));
            }
//            userDO.setAreaId(olduserDO.getAreaId());
            userDO.setCreateAt(olduserDO.getCreateAt());
            userDO.setUpdateAt(System.currentTimeMillis());
            save(userDO);

            return ResultDTO.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.failed();
        }
    }


    @Override
    public ResultDTO update(String realname, String email, String phoneNumber) throws Exception {
        return null;
    }

    @Override
    public ResultDTO resetPwd(String oldPwd, String newPwd) throws Exception {
        return null;
    }

    @Override
    public ResultDTO changeLocked(String id) throws Exception {
        return null;
    }


    /**
     * 根据用户名查询用户信息是否存在
     *
     * @param username
     * @return
     */
    public boolean existsUsername(String username) {
        UserDO userDO = getBaseMapper().selectOne(
                Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username));
        if (userDO == null) {
            return false;
        }
        return true;
    }
//
//    /**
//     * 保存用户
//     *
//     * @param userDO 用户实体
//     * @param roleId 角色id
//     * @return
//     */
//    @Override
//    public ResultDTO saveUser(UserDO userDO, String roleId) {
//        try {
//
//            if (existsUsername(userDO.getUsername())) {
//                return ResultDTO.failed("账号已存在");
//            }
//            userDO.setPassword(new BCryptPasswordEncoder().encode(userDO.getPassword()));
//            save(userDO);
//            return ResultDTO.success();
//        } catch (Exception e) {
//            log.error("保存用户出现异常,异常信息为:{}", e.getMessage());
//            throw e;
//        }
//    }
}