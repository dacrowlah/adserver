package com.iheartjane.processors;

import com.iheartjane.models.Campaign;
import com.iheartjane.validators.CampaignValidator.ValidationFailureReason;
import com.iheartjane.validators.CampaignValidators;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class CampaignValidationProcessor {
  private final CampaignValidators campaignValidators;

  @Inject
  public CampaignValidationProcessor(
      CampaignValidators campaignValidators
  ) {
    this.campaignValidators = campaignValidators;
  }

  public List<ValidationFailureReason> accept(Campaign campaign) {
    return campaignValidators
        .stream()
        .map(v -> v.accept(campaign))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());

  }
}
