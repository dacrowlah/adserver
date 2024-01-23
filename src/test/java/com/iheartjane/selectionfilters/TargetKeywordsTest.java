package com.iheartjane.selectionfilters;

import static com.iheartjane.api.SelectionFilter.FilterReason.MISSING_TARGETED_KEYWORDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.Test;

@MicronautTest
public class TargetKeywordsTest {
  @Inject
  CampaignService campaignService;

  @Inject
  TargetKeywords filter;

  @Test
  public void testSuccess() {
    AdRequest adRequest = new AdRequest();
    adRequest.setKeywords(Set.of("iphone"));

    Campaign campaign = new Campaign();
    campaign.setTargetKeywords(Set.of("iphone"));

    campaignService.addCampaign(campaign);

    var result = filter.accept(campaign, adRequest);
    assertTrue(result.isEmpty());
  }

  @Test
  public void testFailure() {
    AdRequest adRequest = new AdRequest();
    adRequest.setKeywords(Set.of("iphone"));

    Campaign campaign = new Campaign();
    campaign.setTargetKeywords(Set.of("android"));

    campaignService.addCampaign(campaign);

    var result = filter.accept(campaign, adRequest);
    assertTrue(result.isPresent());
    assertEquals(MISSING_TARGETED_KEYWORDS, result.get());
  }
}
