package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_START_TIME;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class StartTimeValidator implements CampaignValidator {
  private static Logger logger = getLogger(StartTimeValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_START_TIME);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    var startTime = campaign.getStartTimestamp();
    var endTime = campaign.getEndTimestamp();

    if (startTime <= 0 || startTime >= endTime) {
      logger.warn("Campaign Validation Failure: {}", REASON.get());
      return REASON;
    }

    return Optional.empty();
  }
}
