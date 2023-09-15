package com.github.shadow.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.shadow.util.SwaggerUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;

import lombok.AllArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@AllArgsConstructor
public class SwaggerConfig {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket shadowDocket() {
        return docket("系统模块", Collections.singletonList("com.github.shadow.controller" + ".system"));
    }

    @Bean
    public Docket commonDocket() {
        return docket("通用模块", Collections.singletonList("com.github.shadow.controller" + ".common"));
    }

    private Docket docket(String groupName, List<String> basePackages) {
        return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
            .apis(SwaggerUtil.basePackages(basePackages)).paths(PathSelectors.any())
            // .securityContexts(securityContexts()).securitySchemes(securitySchemas())
            .build().extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("SHADOW APIS").description("后台接口文档")
            .termsOfServiceUrl("https://flankx.github.io").version("1.0").build();
    }

}
