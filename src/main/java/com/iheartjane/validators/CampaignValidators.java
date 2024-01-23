package com.iheartjane.validators;

import com.iheartjane.api.CampaignValidator;
import com.iheartjane.processors.DuplicateCampaignValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.ArrayList;

/**
 * CampaignValidators is a resource bundle of the currently available campaign validators.  The
 * only time this should change is when a campaign validator is added or removed.
 */
@Singleton
public class CampaignValidators extends ArrayList<CampaignValidator> {
  @Inject
  public CampaignValidators(
      CpmValidator cpmValidator,
      EndTimeValidator endTimeValidator,
      KeywordsValidator keywordsValidator,
      MaxImpressionValidator maxImpressionValidator,
      StartTimeValidator startTimeValidator,
      DuplicateCampaignValidator duplicateCampaignValidator
  ) {
    add(cpmValidator);
    add(endTimeValidator);
    add(keywordsValidator);
    add(maxImpressionValidator);
    add(startTimeValidator);
    add(duplicateCampaignValidator);
  }
}
