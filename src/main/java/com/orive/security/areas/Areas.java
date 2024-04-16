package com.orive.security.areas;

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

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "areas")
public class Areas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long areasId;
	
	@Column(name = "areaname")
	private String areaname;
	
	@Column(name = "description", length = 4000)
	private String description;
}
