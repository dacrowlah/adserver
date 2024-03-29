package com.iheartjane.validators;

import static com.iheartjane.api.CampaignValidator.ValidationFailureReason.INVALID_START_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class StartTimeValidatorTest {

  @Inject
  StartTimeValidator validator;

  @Test
  public void testValidStartTimeSuccess() {
    Campaign c = new Campaign();
    c.setStartTimestamp(1L);
    c.setEndTimestamp(2L);
    var reason = validator.accept(c);
    assertTrue(reason.isEmpty());
  }

  @Test
  public void testInvalidStartTimeFailure() {
    Campaign c = new Campaign();
    c.setStartTimestamp(-1L);
    c.setEndTimestamp(2L);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_START_TIME, reason.get());
  }

  @Test
  public void testStartTimeEqualToEndTimeFailure() {
    Campaign c = new Campaign();
    c.setStartTimestamp(2L);
    c.setEndTimestamp(2L);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_START_TIME, reason.get());
  }
}
