package com.iheartjane.processors;

import com.iheartjane.api.CampaignValidationProcessor;
import com.iheartjane.models.Campaign;
import com.iheartjane.api.CampaignValidator.ValidationFailureReason;
import com.iheartjane.validators.CampaignValidators;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class DefaultCampaignValidationProcessor implements CampaignValidationProcessor {
  private final CampaignValidators campaignValidators;

  @Inject
  public DefaultCampaignValidationProcessor(
      CampaignValidators campaignValidators
  ) {
    this.campaignValidators = campaignValidators;
  }

  @Override
  public List<ValidationFailureReason> accept(Campaign campaign) {
    return campaignValidators
        .stream()
        .map(v -> v.accept(campaign))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }
}
