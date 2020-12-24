package com.bank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo apiInfo() {
		// TODO Auto-generated method stub
		 return new ApiInfoBuilder().
				 title("Account Transfer API").
				 description(" These apis are for getting account balance and transfer money from one account to another").build();
	}
	 @Bean
	    UiConfiguration uiConfig() {
	        return UiConfigurationBuilder.builder()
	            .displayRequestDuration(true)
	            .validatorUrl("")
	            .build();
	    }

}
