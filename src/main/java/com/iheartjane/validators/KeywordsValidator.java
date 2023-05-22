package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.MISSING_KEYWORDS;
import static java.util.Optional.of;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class KeywordsValidator implements CampaignValidator {
  private static final Optional<ValidationFailureReason> REASON = of(MISSING_KEYWORDS);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.getTargetKeywords().isEmpty()) {
      return REASON;
    }

    return Optional.empty();
  }
}
