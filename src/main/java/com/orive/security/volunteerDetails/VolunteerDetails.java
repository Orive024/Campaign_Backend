package com.orive.security.volunteerDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "volunteer_details")
public class VolunteerDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serialnumber;
	
	@Column(name = "volunteername")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private String volunteername;
	
	@Column(name = "volunteerid")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private String volunteerid;
	
	@Column(name = "gender")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private String gender;
	
	@Column(name = "address")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private String address;
	
	@Column(name = "mobilenumber")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private Long mobilenumber;
	
	@Column(name = "emailaddress")
	//@Convert(converter = VolunteerDetailsAesEncryptor.class)
	private String emailaddress;
}
