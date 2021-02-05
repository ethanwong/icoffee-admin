package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.mapper.AuthorityMapper;
import com.icoffee.system.service.AuthorityService;
import com.icoffee.system.service.MenuService;
import com.icoffee.system.service.RoleAuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResultDto batchSaveAuthorityList(List<Authority> authorityList) {
        try {
            List<Authority> batchSaveList = new ArrayList<>();
            for (Authority authority : authorityList) {
                Authority checkAuthoriy = getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().eq(Authority::getUri, authority.getUri()).eq(Authority::getMethod, authority.getMethod()));
                if (checkAuthoriy != null) {
                    authority.setId(checkAuthoriy.getId());
                    authority.setCreateAt(checkAuthoriy.getCreateAt());
                    authority.setUpdateAt(System.currentTimeMillis());
                    this.saveOrUpdate(authority);
                } else {
                    batchSaveList.add(authority);
                }
            }
            saveBatch(batchSaveList);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("批量保存权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }
}
