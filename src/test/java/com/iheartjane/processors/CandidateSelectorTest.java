package com.iheartjane.processors;

import static com.iheartjane.fixtures.Campaigns.TARGET_KEYWORDS;
import static com.iheartjane.fixtures.Campaigns.validCampaign;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.api.CandidateSelector;
import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class CandidateSelectorTest {
  @Inject
  CampaignService campaignService;

  @Inject
  CandidateSelector processor;

  @AfterEach
  public void clear() {
    campaignService.clear();
  }

  @Test
  public void testSuccess() {
    Campaign c = validCampaign();
    AdRequest adRequest = new AdRequest();
    adRequest.setKeywords(TARGET_KEYWORDS);

    campaignService.addCampaign(c);

    var result = processor.accept(adRequest);

    assertFalse(result.isEmpty());
  }

  @Test
  public void testFailure() {
    Set<String> keywords = Set.of("iphone");
    long currentEpoch = System.currentTimeMillis() / 1000;
    long start = currentEpoch - 100;
    long end = currentEpoch - 30;
    AdRequest adRequest = new AdRequest();
    adRequest.setKeywords(keywords);

    Campaign c = new Campaign();
    c.setTargetKeywords(keywords);
    c.setStartTimestamp(start);
    c.setEndTimestamp(end);

    campaignService.addCampaign(c);

    var result = processor.accept(adRequest);

    assertTrue(result.isEmpty());
  }
}
