package com.tika.barcode.config;

import org.springframework.context.annotation.Configuration;


import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * Processes an {@link CorsConfig} request.
 * @author Raghu
 *
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	
	  @Override public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**") .allowedOrigins("*") // Allow requests from your React development server
	   .allowedMethods("GET", "POST", "PUT", "DELETE"); }
	 
}

