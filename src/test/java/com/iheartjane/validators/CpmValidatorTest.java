package com.iheartjane.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CpmValidatorTest {

  @Inject
  CpmValidator validator;

  @Test
  public void testNegativeValueFailure() {
    Campaign c = new Campaign();
    c.setCpm(-1.0f);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
  }

  @Test
  public void testZeroValueFailure() {
    Campaign c = new Campaign();
    c.setCpm(0f);
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
  }

  @Test
  public void testPositiveValueSuccess() {
    Campaign c = new Campaign();
    c.setCpm(1.0f);
    var reason = validator.accept(c);
    assertTrue(reason.isEmpty());
  }

}
