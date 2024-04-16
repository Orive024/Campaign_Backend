package com.orive.security.campaignAnalysis;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "threat")
public class Threat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long threatId;
	
	private String threat;
	
	private String note;
}
