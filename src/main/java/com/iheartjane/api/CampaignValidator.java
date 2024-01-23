package com.iheartjane.api;

import com.iheartjane.models.Campaign;
import java.util.Optional;

public interface CampaignValidator {
  String CAMPAIGN_VALIDATION_FAILURE_MSG = "Campaign Validation Failure: {}";
  enum ValidationFailureReason {
    INVALID_CPM,
    INVALID_START_TIME,
    INVALID_END_TIME,
    MISSING_KEYWORDS,
    INVALID_MAX_IMPRESSIONS,
    DUPLICATE_CAMPAIGN_EXISTS
  }

  public Optional<ValidationFailureReason> accept(Campaign campaign);
}
