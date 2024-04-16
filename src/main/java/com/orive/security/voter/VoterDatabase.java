package com.orive.security.voter;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "voterdatabase")
public class VoterDatabase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sl;
	
	@Column(name = "register_voter")
	private String register_voter;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "areaname")
	private String areaname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "contacted")
	private String contacted;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "blood_group")
	private String blood_group;
	
	@Column(name = "voter_id")
	private String voter_id;
	
	@Column(name = "voter_categories")
	private String voter_categories;
	
	@Column(name = "phone")
	private Long phone;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "birthdate")
	private LocalDate birthdate;
	
	@Lob
	@Column(name = "image_upload",length = 200000)
	private byte[] image_upload;

}
