package com.orive.security.volunteers;

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
public class VolunteersDto {

	
    private Long sl;
	private String name;
	private String gender;
	private String email;
	private String address;
	private String areaname;
	private Long phone;
	private String password;
	private String profile;
	private byte[] image_upload;

}
