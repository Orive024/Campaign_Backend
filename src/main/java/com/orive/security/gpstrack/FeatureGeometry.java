package com.orive.security.gpstrack;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class FeatureGeometry {

	private String type;
    private Double[] coordinates;
}
