package com.iheartjane.processors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CampaignValidationProcessorTest {
  @Inject
  CampaignValidationProcessor processor;

  @Test
  public void testSuccess() {
    var c = successfulIphoneCampaign();
    var result = processor.accept(c);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testMaxImpressionFailure() {
    var c = successfulIphoneCampaign();
    c.setMaxImpression(0);
    var result = processor.accept(c);

    assertFalse(result.isEmpty());
    assertTrue(result.size() == 1);
  }

  private Campaign successfulIphoneCampaign() {
    var c = new Campaign();

    c.setStartTimestamp(1);
    c.setEndTimestamp(Long.MAX_VALUE);
    c.setTargetKeywords(Set.of("iphone"));
    c.setCpm(Float.MAX_VALUE);
    c.setMaxImpression(Long.MAX_VALUE);

    return c;
  }
}
