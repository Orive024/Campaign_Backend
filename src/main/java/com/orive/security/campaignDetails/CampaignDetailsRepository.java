package com.orive.security.campaignDetails;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface CampaignDetailsRepository extends JpaRepository<CampaignDetails, Long> {

	//query to exist by campaignName and CampaignDate
    boolean existsByCampaignnameAndCampaigndate(String campaignname, String campaigndate);

  //query to find CampaignDetails By CampaignId
    @Query("SELECT c FROM CampaignDetails c WHERE c.campaigndetailsid = :campaigndetailsid")
    List<CampaignDetails> findByCampaigndetailsId(@Param("campaigndetailsid") Long campaigndetailsid);

  //query to find CampaignDetails By CampaignName And CampaignDate
    @Query("SELECT c FROM CampaignDetails c WHERE c.campaignname = :campaignname AND c.campaigndate = :campaigndate")
    Optional<CampaignDetails> findByCampaignNameAndDate(@Param("campaignname") String campaignname, @Param("campaigndate") String campaigndate);
}
