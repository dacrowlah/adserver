package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.INVALID_END_TIME;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.api.CampaignValidator;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class EndTimeValidator implements CampaignValidator {
  private static Logger logger = getLogger(EndTimeValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_END_TIME);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    var startTime = campaign.getStartTimestamp();
    var endTime = campaign.getEndTimestamp();

    if (endTime <= 0 || endTime <= startTime) {
      logger.warn(CAMPAIGN_VALIDATION_FAILED_BECAUSE_OF, INVALID_END_TIME);
      return REASON;
    }

    return Optional.empty();
  }
}
