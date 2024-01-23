package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.DUPLICATE_CAMPAIGN_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.Test;

@MicronautTest
public class DuplicateCampaignValidatorTest {
  @Inject
  CampaignService campaignService;

  @Inject
  DuplicateCampaignValidator validator;

  private Campaign basicCampaign = new Campaign(
      null,
      1L,
      3L,
      Set.of("iphone5", "5G"),
      1L,
      0.001f
  );

  @Test
  public void testDuplicateCampaignFailure() {
    campaignService.addCampaign(basicCampaign);
    var reason = validator.accept(basicCampaign);
    assertTrue(reason.isPresent());
    assertEquals(DUPLICATE_CAMPAIGN_EXISTS, reason.get());
  }

  @Test
  public void testNoDuplicateCampaignSuccess() {
    var reason = validator.accept(basicCampaign);
    assertTrue(reason.isEmpty());
  }
}
