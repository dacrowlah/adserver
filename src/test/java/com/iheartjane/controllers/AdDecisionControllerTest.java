package com.iheartjane.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.services.CampaignService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@MicronautTest
public class AdDecisionControllerTest {
  @Inject
  @Client("/")
  HttpClient client;

  @Inject
  CampaignService campaignService;

  @Inject
  AdDecisionController controller;


  @AfterEach
  public void clear() {
    campaignService.clear();
  }

  private Set<String> targetKeywords = Set.of("iphone5");
  private Campaign validCampaign = new Campaign(
      null,
      currentTimestamp() - 10,
      currentTimestamp() + 10,
      targetKeywords,
      1L,
      0.001f
  );

  private Campaign invalidCampaign = new Campaign(
      null,
      -1L,
      -3L,
      Set.of(), //empty set
      -1L,
      -0.001f
  );

  @Test
  public void testDirectAdRequestSuccess() {
    AdRequest adRequest = new AdRequest();
    adRequest.setKeywords(targetKeywords);
    campaignService.addCampaign(validCampaign);

    var response = controller.getAd(adRequest);
    var body = response.body();
    assertEquals(HttpStatus.OK, response.status());
    assertEquals(1001, response.body().getCampaignId());
  }

  @Test
  public void testHttpAdRequestSuccess() {
    String requestBody = "{\"keywords\": [\"iphone5\"]}";
    campaignService.addCampaign(validCampaign);
    var request = HttpRequest.POST("/addecision", requestBody);
    var response = client.toBlocking().retrieve(request);
    var expectedStart = "{\"campaign_id\":1001,\"impression_url\":";
    assertTrue(response.startsWith(expectedStart));
  }

  private long currentTimestamp() {
    return System.currentTimeMillis() / 1000;
  }
}
