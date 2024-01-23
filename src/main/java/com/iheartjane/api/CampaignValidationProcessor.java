package com.iheartjane.api;

import com.iheartjane.api.CampaignValidator.ValidationFailureReason;
import com.iheartjane.models.Campaign;
import java.util.List;

public interface CampaignValidationProcessor {

  List<ValidationFailureReason> accept(Campaign campaign);
}
