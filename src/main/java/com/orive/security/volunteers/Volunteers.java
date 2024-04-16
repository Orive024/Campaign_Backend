package com.orive.security.volunteers;

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

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "volunteers")
public class Volunteers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sl;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "areaname")
	private String areaname;
	
	@Column(name = "phone")
	private Long phone;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "profile")
	private String profile;
	
	@Lob
	@Column(name = "image_upload",length = 200000)
	private byte[] image_upload;

}
