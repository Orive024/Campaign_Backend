package com.orive.security.gpstrack;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class FeatureProperties {

	private String title;
}
