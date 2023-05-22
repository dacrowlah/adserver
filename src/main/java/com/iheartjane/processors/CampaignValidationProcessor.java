package com.iheartjane.processors;

import com.iheartjane.models.Campaign;
import com.iheartjane.validators.CampaignValidator;
import com.iheartjane.validators.CampaignValidator.ValidationFailureReason;
import com.iheartjane.validators.CpmValidator;
import com.iheartjane.validators.DuplicateCampaignValidator;
import com.iheartjane.validators.EndTimeValidator;
import com.iheartjane.validators.KeywordsValidator;
import com.iheartjane.validators.MaxImpressionValidator;
import com.iheartjane.validators.StartTimeValidator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class CampaignValidationProcessor {
  private final List<CampaignValidator> validators = new ArrayList<>();

  @Inject
  public CampaignValidationProcessor(
      CpmValidator cpmValidator,
      EndTimeValidator endTimeValidator,
      KeywordsValidator keywordsValidator,
      MaxImpressionValidator maxImpressionValidator,
      StartTimeValidator startTimeValidator,
      DuplicateCampaignValidator duplicateCampaignValidator
  ) {
    validators.add(cpmValidator);
    validators.add(endTimeValidator);
    validators.add(keywordsValidator);
    validators.add(maxImpressionValidator);
    validators.add(startTimeValidator);
    validators.add(duplicateCampaignValidator);
  }

  public List<ValidationFailureReason> accept(Campaign campaign) {
    return validators
        .stream()
        .map(v -> v.accept(campaign))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());

  }
}
