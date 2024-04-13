package com.orive.security.gpstrack;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
public class Feature {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featuresid;

	@Column(name = "type")
    private String type;

//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name = "collecionid", nullable = false)
//	private FeatureCollection featureCollection;
	
	@Transient
	private List<FeatureCollection> featureCollections=new ArrayList<>();
}
