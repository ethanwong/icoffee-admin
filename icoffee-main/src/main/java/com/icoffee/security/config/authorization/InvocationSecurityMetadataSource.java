package com.icoffee.security.config.authorization;

import com.icoffee.system.domain.Authority;
import com.icoffee.system.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Name InvocationSecurityMetadataSource
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 10:43
 */
@Component
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private AuthorityService authorityService;

    /**
     * 加载系统全局鉴权资源
     */
    @PostConstruct
    private void loadResourceDefine() {

        List<Authority> allAuthority = (List<Authority>) authorityService.selectAll().getData();
        if (resourceMap == null) {
            resourceMap = new HashMap<>();
            for (Authority authority : allAuthority) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<>();
                ConfigAttribute configAttribute = new SecurityConfig(authority.getPermission());
                configAttributes.add(configAttribute);
                resourceMap.put(authority.getUri() + "#" + authority.getMethod(), configAttributes);
            }
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null) {
            loadResourceDefine();
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            RequestMatcher requestMatcher = null;
            String resURL = ite.next();
            try {
                String[] urls = StringUtils.split(resURL, "#");
                requestMatcher = new AntPathRequestMatcher(urls[0], urls[1]);
            } catch (Exception e) {
                requestMatcher = new AntPathRequestMatcher(resURL);
            }

            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }
        return null;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
