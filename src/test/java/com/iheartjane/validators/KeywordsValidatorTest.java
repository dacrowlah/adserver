package com.iheartjane.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.Test;

@MicronautTest
public class KeywordsValidatorTest {
  @Inject
  KeywordsValidator validator;

  @Test
  public void testEmptyKeywordsFailure() {
    Campaign c = new Campaign();
    c.setTargetKeywords(Set.of());
    var reason = validator.accept(c);
    assertTrue(reason.isPresent());
  }

  @Test
  public void testNonEmptyKeywordsSuccess() {
    Campaign c = new Campaign();
    c.setTargetKeywords(Set.of("iphone"));
    var reason = validator.accept(c);
    assertTrue(reason.isEmpty());
  }
}
