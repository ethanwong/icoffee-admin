package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.domain.RoleAuthority;
import com.icoffee.system.mapper.AuthorityMapper;
import com.icoffee.system.service.AuthorityService;
import com.icoffee.system.service.MenuService;
import com.icoffee.system.service.RoleAuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Name AuthorityServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-27 9:13
 */
@Service
@Log4j2
public class AuthorityServiceImpl extends MpBaseServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Override
    public ResultDto saveEntity(Authority authority) {
        try {
            Authority checkAuthoriy = getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().eq(Authority::getUri, authority.getUri()).eq(Authority::getMethod, authority.getMethod()));
            if (checkAuthoriy != null) {
                return ResultDto.returnFail(authority.getName() + ":uri与方法重复");
            } else if (getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().eq(Authority::getName, authority.getName())) != null) {
                return ResultDto.returnFail(authority.getName() + "：名称重复");
            } else if (getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().eq(Authority::getMenuId, authority.getMenuId()).eq(Authority::getModule, authority.getModule())) != null) {
                return ResultDto.returnFail(authority.getName() + "：模块重复");
            }
            if (StringUtils.isBlank(authority.getId())) {
                authority.setId(UUID.randomUUID().toString().replace("-", ""));
            }
            save(authority);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("保存权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto updateEntity(Authority authority) {
        try {
            Authority checkAuthoriy = getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().ne(Authority::getId, authority.getId()).eq(Authority::getUri, authority.getUri()).eq(Authority::getMethod, authority.getMethod()));
            if (checkAuthoriy != null) {
                return ResultDto.returnFail(authority.getName() + ":uri与方法重复");
            } else if (getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().ne(Authority::getId, authority.getId()).eq(Authority::getName, authority.getName())) != null) {
                return ResultDto.returnFail(authority.getName() + "：名称重复");
            }
            updateById(authority);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("修改权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto batchSave(String menuId) {
        try {
            if (StringUtils.isBlank(menuId)) {
                return ResultDto.returnFail("菜单ID不能为空！");
            }

            Menu menu = menuService.getById(menuId);
            String uri = "";
            String name = menu.getTitle();
            String repeat = "";

            String oneKey[] = {"POST", "DELETE", "PUT", "GET", "GET"};
            String oneKeyName[] = {"增加", "删除", "修改", "分页", "详情"};
            String oneKeyUri[] = {"/create", "/delete", "/update", "/page", "/detail"};
            String modules[] = {"create", "delete", "edit", "page", "detail"};
            Authority authority = null;

            List<String> uriList = new ArrayList<>();
            List<String> methodList = new ArrayList<>();
            List<Authority> authorityList = getBaseMapper().selectList(Wrappers.<Authority>lambdaQuery());

            for (Authority auth : authorityList) {
                uriList.add(auth.getUri());
                methodList.add(auth.getMethod());
            }

            for (int i = 0; i < oneKey.length; i++) {
                if (oneKeyUri[i] != "") {
                    authority = new Authority(name + oneKeyName[i], uri + oneKeyUri[i], oneKey[i], menuId, modules[i]);
                } else {
                    authority = new Authority(name + oneKeyName[i], uri, oneKey[i], menuId, modules[i]);
                }
                if (uriList.contains(authority.getUri()) && methodList.contains(authority.getMethod())) {
                    repeat += authority.getName() + ",";
                } else {
                    save(authority);
                }
            }

            if ("".equals(repeat)) {

                return ResultDto.returnSuccess();
            } else {
                return ResultDto.returnFail(repeat + "以上接口重复");
            }
        } catch (Exception e) {
            log.error("批量保存权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto batchDelete(List<String> ids) {
        try {
            List<RoleAuthority> authorityList = roleAuthorityService.getBaseMapper().selectList(Wrappers.<RoleAuthority>lambdaQuery().in(RoleAuthority::getAuthorityId, ids));
            if (authorityList.size() > 0) {
                return ResultDto.returnFail("失败：已被角色关联");
            }
            getBaseMapper().deleteBatchIds(ids);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("批量删除权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }
}
