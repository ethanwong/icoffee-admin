package com.icoffee.system.service.impl;

import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.RoleAuthority;
import com.icoffee.system.mapper.RoleAuthorityMapper;
import com.icoffee.system.service.RoleAuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @Name RoleAuthorityServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-27 15:31
 */
@Service
@Log4j2
public class RoleAuthorityServiceImpl extends MpBaseServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {
}
