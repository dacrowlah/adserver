package com.iheartjane.processors;

import com.iheartjane.models.AdResponse;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.UUID;

@Singleton
public class AdResponder {
  private static final String IMPRESSION_URL_FORMAT = "http://localhost:8000/impression?campaignId=%s&impressionId=%s";
  public AdResponse accept(Campaign campaign) {

    return AdResponse
        .builder()
        .campaignId(campaign.getCampaignId())
        .impressionUrl(buildImpressionUrl(campaign))
        .build();
  }

  private String buildImpressionUrl(Campaign campaign) {
    var impressionId = UUID.randomUUID().toString();
    var campaignId = campaign.getCampaignId();
    return String.format(IMPRESSION_URL_FORMAT, campaignId, impressionId);
  }
}
