package com.icoffee.security.config.security.authorization;

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
 * @Name CustomAccessDecisionManager
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 10:52
 */
@Component
public class SecurityAccessDecisionManager implements AccessDecisionManager {


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            String needPermission = configAttribute.getAttribute();
            System.out.println("SecurityAccessDecisionManager haveAuthorities:" + authentication.getAuthorities());
            System.out.println("SecurityAccessDecisionManager needPermission:" + needPermission);
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needPermission.trim().equals(grantedAuthority.getAuthority().trim())) {
                    return;
                }
            }
            throw new AccessDeniedException("No Access");
        }
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
