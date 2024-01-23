package com.iheartjane.api;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import java.util.Optional;

public interface SelectionFilter {
  enum FilterReason {
    CURRENT_TIMESTAMP_NOT_IN_RANGE,
    MISSING_TARGETED_KEYWORDS,
    CAMPAIGN_IMPRESSION_CAP_HIT,
  }

  String CAMPAIGN_ID_FILTERED_FOR = "CampaignId: {} Filtered: {}";

  public Optional<FilterReason> accept(Campaign campaign, AdRequest adRequest);
}
