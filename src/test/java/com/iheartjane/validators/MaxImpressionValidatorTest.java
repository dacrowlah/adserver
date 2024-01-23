package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.INVALID_MAX_IMPRESSIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class MaxImpressionValidatorTest {

  @Inject
  MaxImpressionValidator validator;

  @Test
  public void testValidMaxImpressionSuccess() {
    Campaign c = new Campaign();
    c.setMaxImpression(10);
    var reason = validator.accept(c);
    assertTrue(reason.isEmpty());
  }

  @Test
  public void testInvalidMaxImpressionFailure() {
    Campaign c = new Campaign();
    c.setMaxImpression(-1);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_MAX_IMPRESSIONS, reason.get());
  }
}
