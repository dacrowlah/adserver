package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.DUPLICATE_CAMPAIGN_EXISTS;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.api.CampaignValidator;
import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class DuplicateCampaignValidator implements CampaignValidator {
  private static Logger logger = getLogger(DuplicateCampaignValidator.class);
  private static final Optional<ValidationFailureReason> REASON = of(DUPLICATE_CAMPAIGN_EXISTS);

  private final CampaignService campaignService;
  public DuplicateCampaignValidator(CampaignService campaignService) {
    this.campaignService = campaignService;
  }

  @Override
  public Optional<ValidationFailureReason> accept(Campaign campaign) {
    if (campaignService.exists(campaign)) {
      logger.warn(CAMPAIGN_VALIDATION_FAILED_BECAUSE_OF, DUPLICATE_CAMPAIGN_EXISTS);
      return REASON;
    }

    return Optional.empty();
  }
}
