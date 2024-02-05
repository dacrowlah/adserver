package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.INVALID_CPM;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.api.CampaignValidator;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class CpmValidator implements CampaignValidator {
  private static Logger logger = getLogger(CpmValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(INVALID_CPM);
  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaign.getCpm() <= 0) {
      logger.warn(CAMPAIGN_VALIDATION_FAILED_BECAUSE_OF, INVALID_CPM);
      return REASON;
    }

    return Optional.empty();
  }
}
