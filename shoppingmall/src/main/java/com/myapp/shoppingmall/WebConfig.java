package com.myapp.shoppingmall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		// Controller 대신에 View를 Mapping 가능
//		registry.addViewController("/").setViewName("home");
//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 저장된 파일(이미지)의 경로를 미리 입력 (이미지를 편리하게 사용하기 위해)
		registry.addResourceHandler("/media/**")
				.addResourceLocations("file:///C:/java502/spring/Spring WorkSpace/shoppingmall/src/main/resources/static/media/");
	}
}
