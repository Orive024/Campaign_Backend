package com.orive.security.campaignDetails;

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
@Table(name = "campaign_details")
public class CampaignDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long campaigndetailsid;
	
	@Column(name = "campaignname")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String campaignname;
	
	@Column(name = "campaigntype")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String campaigntype;
	
	@Column(name = "campaigndate")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String campaigndate;
	
	@Column(name = "campaignobjective")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String campaignobjective;
	
	@Column(name = "starttime")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String starttime;
	
	@Column(name = "endtime")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String endtime;
	
	@Column(name = "location")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String location;
	
	@Column(name = "campaignbudget")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private Long campaignbudget;
	
	@Column(name = "volunteersassigned")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String volunteersassigned;
	
	@Column(name = "campaignmaterials")
	//@Convert(converter = CampaignDetailsAesEncryptor.class)
	private String campaignmaterials;
	
//	@Lob
//	@Column(name = "upload_doc",  length = 100000)
//	private byte[] uploadDoc;
}
