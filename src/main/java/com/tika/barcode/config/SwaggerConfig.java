package com.tika.barcode.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


/**
 * Processes an {@link SwaggerConfig } request.
 * @author Raghu
 *
 */

@Configuration
//@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig  {

	 @Bean
	    public GroupedOpenApi controllerApi() {
	        return GroupedOpenApi.builder()
	                .group("controller-api")
	                .packagesToScan("com.nextscience.controller") // Specify the package to scan
	                .build();
	    }
	 
		
		  @Bean public OpenAPI springShopOpenAPI() { return new OpenAPI() .info(new
		  Info().title("NextScience API") .description("NextScience application")
		  .version("v0.0.1") .license(new
		  License().name("Apache 2.0").url("http://www.apache.org/licenses/"))) .externalDocs(new
		  ExternalDocumentation() .description("NextScience Documentation")
		  .url("http://localhost:9090/api-docs/controller-api")); }
		 
}
