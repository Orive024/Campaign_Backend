package com.orive.security.voter;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
public class VoterDatabaseDto {

    private Long sl;
	private String register_voter;
	private String name;
	private String areaname;
	private String email;
	private String contacted;
	private String address;
	private String blood_group;
	private String voter_id;
	private String voter_categories;
	private Long phone;
	private String gender;
	private LocalDate birthdate;
	private byte[] image_upload;
}
