package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.CAMPAIGN_IMPRESSION_CAP_HIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.models.ImpressionSignature;
import com.iheartjane.services.CampaignService;
import com.iheartjane.services.ImpressionService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class ImpressionCapTest {
  @Inject
  CampaignService campaignService;

  @Inject
  ImpressionCap filter;

  @Inject
  ImpressionService impressionService;

  private final Campaign campaign = new Campaign() {{
    setMaxImpression(1L);
    setCampaignId(1001);
  }};

  @AfterEach
  public void clear() {
    campaignService.clear();
  }

  @Test
  public void testImpressionCapNotHit() {
    campaignService.addCampaign(campaign);

    var result = filter.accept(campaign, new AdRequest());
    assertTrue(result.isEmpty());
  }

  @Test
  public void testImpressionCapHit() {
    campaignService.addCampaign(campaign);
    var signature = new ImpressionSignature(1001, "fake-uuid");
    impressionService.trackImpression(signature);
    var result = filter.accept(campaign, new AdRequest());
    assertTrue(result.isPresent());
    assertEquals(CAMPAIGN_IMPRESSION_CAP_HIT, result.get());
  }
}
