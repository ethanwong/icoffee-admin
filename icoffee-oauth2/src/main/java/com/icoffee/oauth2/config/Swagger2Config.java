package com.icoffee.oauth2.config;

import com.icoffee.oauth2.common.BaseSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name Swagger2Configuration
 * @Description swagger2配置
 * @Author chenly
 * @Create 2019-12-20 16:00
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwaggerConfig {

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        List<Parameter> parameterList = new ArrayList<>();
        ParameterBuilder authParameter = new ParameterBuilder();
        authParameter.name("Authorization").description("参数值为JWT TOKEN，格式为：Bearer {accessToken}")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameterList.add(authParameter.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.icoffee.oauth2.api"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterList);
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Coffee服务端接口文档")
                .description("API接口需要在请求Header头添加Authorization参数进行鉴权，其中Authorization的参数值为JWT TOKEN，" +
                        "格式为：Bearer {accessToken}，accessToken可通过TOKEN接口获取。")
                .version("1.0")
                .build();
    }
}