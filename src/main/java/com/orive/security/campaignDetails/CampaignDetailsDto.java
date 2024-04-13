package com.orive.security.campaignDetails;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
public class CampaignDetailsDto {
	
    private Long campaigndetailsid;
	private String campaignname;
	private String campaigntype;
	private String campaigndate;
	private String campaignobjective;
	private String starttime;
	private String endtime;
	private String location;
	private Long campaignbudget;
	private String volunteersassigned;
	private String campaignmaterials;

}
