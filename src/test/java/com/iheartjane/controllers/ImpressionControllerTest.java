package com.iheartjane.controllers;

import static com.iheartjane.fixtures.Campaigns.TARGET_KEYWORDS;
import static com.iheartjane.fixtures.Campaigns.validCampaign;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iheartjane.models.AdRequest;
import com.iheartjane.services.CampaignService;
import com.iheartjane.services.ImpressionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class ImpressionControllerTest {
  @Inject
  @Client("/")
  HttpClient client;

  @Inject
  CampaignService campaignService;

  @Inject
  AdDecisionController adDecisionController;

  @Inject
  ImpressionController impressionController;

  @Inject
  ImpressionService impressionService;

  private final String fakeUUID = "fake-uuid-unused";

  @AfterEach
  public void clear() {
    impressionService.clear();
  }

  @Test
  public void testValidImpression() {
    var campaign = validCampaign();
    var adRequest = new AdRequest();
    adRequest.setKeywords(TARGET_KEYWORDS);

    campaignService.addCampaign(campaign);

    var adResponse = adDecisionController.getAd(adRequest).body();
    var response = impressionController.impression(adResponse.getCampaignId(), fakeUUID);

    assertEquals(HttpStatus.OK, response.status());
  }

  @Test
  public void testInvalidImpression() {
    var fakeCampaignId = 2;
    var response = impressionController.impression(fakeCampaignId, fakeUUID);

    assertEquals(HttpStatus.BAD_REQUEST, response.status());
  }

  @Test
  public void testValidImpressionDoesNotGetDoubleCounted() {
    var campaign = validCampaign();
    var adRequest = new AdRequest();
    adRequest.setKeywords(TARGET_KEYWORDS);

    campaignService.addCampaign(campaign);

    var adResponse = adDecisionController.getAd(adRequest).body();
    var response = impressionController.impression(adResponse.getCampaignId(), fakeUUID);

    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
    assertEquals(HttpStatus.OK, response.status());

    impressionController.impression(adResponse.getCampaignId(), fakeUUID);
    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
  }
}
