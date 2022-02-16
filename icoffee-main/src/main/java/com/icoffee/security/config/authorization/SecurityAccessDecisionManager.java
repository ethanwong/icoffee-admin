package com.icoffee.security.config.authorization;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Name SecurityAccessDecisionManager
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 10:52
 */
@Component
@Log4j2
public class SecurityAccessDecisionManager implements AccessDecisionManager {

    /**
     * 检查请求的资源是否有授权
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            String needPermission = configAttribute.getAttribute();
            log.info("SecurityAccessDecisionManager haveAuthorities:" + authentication.getAuthorities());
            log.info("SecurityAccessDecisionManager needPermission:" + needPermission);
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needPermission.trim().equals(grantedAuthority.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no access,need permission " + configAttributes);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
