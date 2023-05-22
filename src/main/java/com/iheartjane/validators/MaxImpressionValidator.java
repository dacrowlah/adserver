package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_MAX_IMPRESSIONS;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class MaxImpressionValidator implements CampaignValidator {
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_MAX_IMPRESSIONS);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.getMaxImpression() <= 0) {
      return REASON;
    }

    return Optional.empty();
  }
}
