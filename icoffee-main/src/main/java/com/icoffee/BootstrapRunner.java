package com.icoffee;

import com.icoffee.common.annotation.AuthorizePoint;
import com.icoffee.common.utils.SpringBeanUtils;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.service.AuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @Name BootstrapRunner
 * @Description
 * @Author huangyingfeng
 * @Create 2021-02-05 11:48
 */
@Log4j2
@Component
public class BootstrapRunner implements ApplicationRunner {

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("********************BootstrapRunner********************");

        //获取所有带@Controller注解的类
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringBeanUtils.getApplicationContext().getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();

        //遍历识别资源授权信息
        List<Authority> authorityList = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            RequestMappingInfo key = infoEntry.getKey();
            HandlerMethod handlerMethod = infoEntry.getValue();

            Set uris = key.getPatternsCondition().getPatterns();
            Object[] uriMethods = key.getMethodsCondition().getMethods().toArray();
            String description = handlerMethod.toString();

            //获取类里面的带@AuthorizePoint注解的方法，获取注解里面配置的参数，生成鉴权信息
            AuthorizePoint authorizePoint = handlerMethod.getMethodAnnotation(AuthorizePoint.class);
            if (authorizePoint != null) {
                //授权名称
                String name = authorizePoint.name();
                //模块名称
                String module = authorizePoint.module();
                //授权标签
                String permission = module + ":" + handlerMethod.getMethod().getName();


                Iterator<String> ite = uris.iterator();
                while (ite.hasNext()) {
                    //请求URI
                    String uri = ite.next();

                    uri = hanleUri(uri);

                    for (Object method : uriMethods) {
                        Authority authority = new Authority(name, uri, method.toString(), permission, description, module);
                        log.info("init authority={}", authority);
                        authorityList.add(authority);
                    }
                }
            }
        }

        /**
         * 批量保存授权信息
         */
        authorityService.batchSaveAuthorityList(authorityList);

    }

    private String hanleUri(String uri) {
        if (uri.contains("{") && uri.contains("}")) {
            return uri.substring(0, uri.lastIndexOf("/"));
        } else {
            return uri;
        }
    }

}
