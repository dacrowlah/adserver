package com.iheartjane.processors;

import com.iheartjane.api.AdResponder;
import com.iheartjane.models.AdResponse;
import com.iheartjane.models.Campaign;
import com.iheartjane.models.ImpressionSignature;
import com.iheartjane.services.ImpressionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.UUID;

@Singleton
public class DefaultAdResponder implements AdResponder {
  private static final String IMPRESSION_URL_FORMAT = "http://localhost:8000/impression?campaignId=%s&impressionId=%s";
  private final ImpressionService impressionService;

  @Inject
  public DefaultAdResponder(ImpressionService impressionService) {
    this.impressionService = impressionService;
  }

  @Override
  public AdResponse accept(Campaign campaign) {
    return AdResponse
        .builder()
        .campaignId(campaign.getCampaignId())
        .impressionUrl(buildImpressionUrl(campaign))
        .build();
  }

  private String buildImpressionUrl(Campaign campaign) {
    var signature = new ImpressionSignature(
        campaign.getCampaignId(),
        UUID.randomUUID().toString()
    );

    impressionService.recordSentImpression(signature);

    return String.format(IMPRESSION_URL_FORMAT, signature.campaignId(), signature.impressionId());
  }
}
