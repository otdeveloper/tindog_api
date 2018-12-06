package com.key.tindog.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
//	private ApiInfo apiInfo() {
//		return new ApiInfo(
//				"Tindog_API",
//				"Rest Api for my personal project - Tindog.",
//				"V1",
//				"None yet.",
//				new Contact("Evgenii", "", "ibanez.n04@gmail.com"),
//				"Personal", "https://github.com/ibanezn04/Tindog_api/blob/master/LICENCE", Collections.emptyList());
//	}
}
