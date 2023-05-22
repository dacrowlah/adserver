package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_END_TIME;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class EndTimeValidator implements CampaignValidator {
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_END_TIME);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    var startTime = campaign.getStartTimestamp();
    var endTime = campaign.getEndTimestamp();

    if (endTime <= 0 || endTime <= startTime) {
      return REASON;
    }

    return Optional.empty();
  }
}
