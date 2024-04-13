package com.orive.security.gpstrack;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "feature_collection")
public class FeatureCollection {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long collecionid;

	    @Column(name = "featuresid")
	    private Long featuresid;
	    
	    @Embedded
	    private FeatureProperties properties;

	    @Embedded
	    private FeatureGeometry geometry;


}
