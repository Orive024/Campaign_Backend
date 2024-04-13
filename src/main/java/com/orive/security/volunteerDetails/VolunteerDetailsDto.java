package com.orive.security.volunteerDetails;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolunteerDetailsDto {

	
    private Long serialnumber;
	private String volunteername;
	private String volunteerid;
	private String gender;
	private String address;
	private Long mobilenumber;
	private String emailaddress;
}
