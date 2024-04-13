package com.orive.security.volunteerDetails;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VolunteerDetailsConfiguration {
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
