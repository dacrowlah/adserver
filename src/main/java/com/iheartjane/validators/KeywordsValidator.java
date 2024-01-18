package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.MISSING_KEYWORDS;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class KeywordsValidator implements CampaignValidator {
  private static Logger logger = getLogger(KeywordsValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(MISSING_KEYWORDS);

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.hasNoKeywords()) {
      logger.warn(CAMPAIGN_VALIDATION_FAILURE_MSG, REASON.get());
      return REASON;
    }

    return Optional.empty();
  }
}
