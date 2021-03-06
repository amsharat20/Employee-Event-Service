	package com.employee.management;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource("classpath:swagger.properties")
@EnableSwagger2
@ComponentScan(basePackages ="com.employee.management")
public class Application {

	private static final String SWAGGER_API_VERSION = "2.0";
	private static final String SWAGGER_LICENSE = "Swagger License";
	private static final String SWAGGER_TITLE = "Employee Management";
	private static final String SWAGGER_DESCRIPTION = "Details about Rest APIs used in Employee Management";

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title(SWAGGER_TITLE).description(SWAGGER_DESCRIPTION).license(SWAGGER_LICENSE).version(SWAGGER_API_VERSION).build();
	}

	@Bean
	public Docket productsApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).pathMapping("/").select().paths(PathSelectors.regex("/api/v1.*")).build();
	}

	static final Logger logger  = LogManager.getLogger(Application.class.getName());

	public static void main(String[] args) {
		logger.info("entered application");
		SpringApplication.run(Application.class, args);
	}
}
