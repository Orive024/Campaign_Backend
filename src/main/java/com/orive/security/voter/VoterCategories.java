package com.orive.security.voter;

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
@Table(name = "voter_categories")
public class VoterCategories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sl;
	
	@Column(name = "categories")
	private String categories;
	
	@Column(name = "description", length = 4000)
	private String description;
}
