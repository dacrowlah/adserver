package com.iheartjane.validators;

import static com.iheartjane.validators.CampaignValidator.ValidationFailureReason.INVALID_END_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class EndTimeValidatorTest {
  @Inject
  EndTimeValidator validator;

  @Test
  public void testNegativeEndTimeValueFailure() {
    Campaign c = new Campaign();
    c.setEndTimestamp(-3l);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_END_TIME, reason.get());
  }

  @Test
  public void testZeroEndTimeValueFailure() {
    Campaign c = new Campaign();
    c.setEndTimestamp(0l);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_END_TIME, reason.get());
  }

  @Test
  public void testEndTimeBeforeStartTimeFailure() {
    Campaign c = new Campaign();
    c.setEndTimestamp(1l);
    c.setStartTimestamp(3l);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
    assertEquals(INVALID_END_TIME, reason.get());
  }

  @Test
  public void testPositiveEndTimeValueSuccess() {
    Campaign c = new Campaign();
    c.setEndTimestamp(3l);
    c.setStartTimestamp(1l);
    var reason = validator.accept(c);
    assertTrue(reason.isEmpty());
  }
}
