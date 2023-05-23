package com.iheartjane.controllers;

import static com.iheartjane.fixtures.Campaigns.TARGET_KEYWORDS;
import static com.iheartjane.fixtures.Campaigns.validCampaign;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.iheartjane.models.AdRequest;
import com.iheartjane.services.CampaignService;
import com.iheartjane.services.ImpressionService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
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
  public void testValidImpression() throws MalformedURLException {
    var campaign = validCampaign();
    var adRequest = new AdRequest();
    adRequest.setKeywords(TARGET_KEYWORDS);

    campaignService.addCampaign(campaign);

    var adResponse = adDecisionController.getAd(adRequest).body();
    var impressionId = getImpressionId(adResponse.getImpressionUrl());
    assertFalse(impressionId.isEmpty());

    var response = impressionController.impression(
        adResponse.getCampaignId(),
        impressionId.get()
    );

    assertEquals(HttpStatus.OK, response.status());
  }

  @Test
  public void testInvalidImpression() {
    var fakeCampaignId = 2;
    var response = impressionController.impression(fakeCampaignId, fakeUUID);

    assertEquals(HttpStatus.BAD_REQUEST, response.status());
  }

  @Test
  public void testValidImpressionDoesNotGetDoubleCounted() throws MalformedURLException {
    var campaign = validCampaign();
    var adRequest = new AdRequest();
    adRequest.setKeywords(TARGET_KEYWORDS);

    campaignService.addCampaign(campaign);

    var adResponse = adDecisionController.getAd(adRequest).body();
    var impressionId = getImpressionId(adResponse.getImpressionUrl());

    assertFalse(impressionId.isEmpty());

    var response = impressionController.impression(
        adResponse.getCampaignId(),
        impressionId.get()
    );

    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
    assertEquals(HttpStatus.OK, response.status());

    impressionController.impression(adResponse.getCampaignId(), fakeUUID);
    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
  }

  private static Optional<String> getImpressionId(String impressionUrl) throws MalformedURLException {
    var queryString = (new URL(impressionUrl)).getQuery();
    String[] pairs = queryString.split("&");
    for (var pair : pairs) {
      String[] parts = pair.split("=");
      if (parts[0].equals("impressionId")) {
        return Optional.of(parts[1]);
      }
    }

    return Optional.empty();
  }
}
