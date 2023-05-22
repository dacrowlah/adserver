package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.DUPLICATE_CAMPAIGN_EXISTS;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class DuplicateCampaignValidator implements CampaignValidator {
  private static final Optional<ValidationFailureReason> REASON = of(DUPLICATE_CAMPAIGN_EXISTS);

  private final CampaignService campaignService;
  public DuplicateCampaignValidator(CampaignService campaignService) {
    this.campaignService = campaignService;
  }

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaignService.exists(campaign)) {
      return REASON;
    }

    return Optional.empty();
  }
}
