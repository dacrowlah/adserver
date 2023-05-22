package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_START_TIME;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class StartTimeValidator implements CampaignValidator {
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_START_TIME);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    var startTime = campaign.getStartTimestamp();
    var endTime = campaign.getEndTimestamp();

    if (startTime <= 0 || startTime >= endTime) {
      return REASON;
    }

    return Optional.empty();
  }
}
