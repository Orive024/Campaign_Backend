package com.orive.security.campaignDetails;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignDetailsConfiguration {
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

}
