package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_CPM;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class CpmValidator implements CampaignValidator {

  private static final Optional<ValidationFailureReason> REASON = of(INVALID_CPM);
  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.getCpm() <= 0) {
      return REASON;
    }

    return Optional.empty();
  }
}
