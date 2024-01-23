package com.iheartjane.processors;

import static java.lang.String.format;

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

    return format(IMPRESSION_URL_FORMAT, signature.campaignId(), signature.impressionId());
  }
}
