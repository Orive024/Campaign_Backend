package com.orive.security.voter;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;


@Configuration
public class VoterConfiguration {
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	 @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        
	        // Set max file size
	        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // 10MB
	        
	        // Set max request size
	        factory.setMaxRequestSize(DataSize.ofMegabytes(10)); // 10MB
	        
	        return factory.createMultipartConfig();
	    }

}