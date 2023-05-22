package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.CAMPAIGN_IMPRESSION_CAP_HIT;
import static java.util.Optional.of;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.services.ImpressionService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Optional;

/**
 * Ensure that, when configured, campaign capping occurs to prevent overspend.
 */
@Singleton
public class ImpressionCap implements SelectionFilter {
  private static final Optional<FilterReason> REASON = of(CAMPAIGN_IMPRESSION_CAP_HIT);
  private final ImpressionService impressionService;

  @Inject
  public ImpressionCap(ImpressionService impressionService) {
    this.impressionService = impressionService;
  }

  /**
   * Ensure that campaign capping occurs to prevent overspend.
   * @param campaign
   * @param adRequest
   * @return an Optional reason for the candidate being removed from consideration.
   */
  @Override
  public Optional<FilterReason> accept(Campaign campaign, AdRequest adRequest) {
    var campaignId = campaign.getCampaignId();
    var totalImpressions = impressionService.getCampaignImpressions(campaignId);

    if (totalImpressions >= campaign.getMaxImpression()) {
      return REASON;
    }

    return Optional.empty();
  }
}