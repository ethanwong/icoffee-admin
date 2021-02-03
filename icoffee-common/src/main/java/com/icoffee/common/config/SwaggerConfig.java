package com.icoffee.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * @Api：用在控制器类上，表示对类的说明 tags="说明该类的作用，可以在UI界面上看到的说明信息的一个好用注解"
 * value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 * @ApiOperation：用在请求的方法上，说明方法的用途、作用 value="说明方法的用途、作用"
 * notes="方法的备注说明"
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面（标注一个指定的参数，详细概括参数的各个方面，例如：参数名是什么？参数意义，是否必填等） name：属性值为方法参数名
 * value：参数意义的汉字说明、解释
 * required：参数是否必须传
 * paramType：参数放在哪个地方
 * · header --> 请求参数的获取：@RequestHeader
 * · query --> 请求参数的获取：@RequestParam
 * · path（用于restful接口）--> 请求参数的获取：@PathVariable
 * · div（不常用）
 * · form（不常用）
 * dataType：参数类型，默认String，其它值dataType="Integer"
 * defaultValue：参数的默认值
 * @ApiResponses：用在请求的方法上，表示一组响应
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：状态码数字，例如400
 * message：信息，例如"请求参数没填好"
 * response：抛出异常的类
 * @ApiModel：用于响应类上（POJO实体类），描述一个返回响应数据的信息（描述POJO类请求或响应的实体说明） （这种一般用在post接口的时候，使用@RequestBody接收JSON格式的数据的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 * @ApiModelProperty：用在POJO属性上，描述响应类的属性说明
 * @ApiIgnore：使用该注解忽略这个API；
 */

/**
 * @Name SwaggerConfig
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-29 14:18
 * @Link @https://github.com/springfox/springfox
 */
@Configuration // 开启Swagger自定义接口文档
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public SecurityConfiguration securityConfiguration() {
        return SecurityConfigurationBuilder.builder()
                .enableCsrfSupport(true)
                .build();
    }

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalRequestParameters(getGlobalRequestParameters());
    }

    /**
     * 生成全局通用参数
     * @return
     */
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("Authorization")
                .description("参数值为JWT TOKEN，格式为：Bearer ${accessToken}")
                .required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return parameters;
    }

    /**
     * API基础信息定义（就是更新Swagger默认页面上的信息）
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RestFul Api Document")
                .description("Swagger3 Api Document.")
                .contact(new Contact("Developer", "", "ethanwong@qq.com"))
                .version("1.0")
                .build();
    }
}
