package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.INVALID_MAX_IMPRESSIONS;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.api.CampaignValidator;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class MaxImpressionValidator implements CampaignValidator {
  private static Logger logger = getLogger(MaxImpressionValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_MAX_IMPRESSIONS);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.getMaxImpression() <= 0) {
      logger.warn(CAMPAIGN_VALIDATION_FAILURE_MSG, REASON.get());
      return REASON;
    }

    return Optional.empty();
  }
}
