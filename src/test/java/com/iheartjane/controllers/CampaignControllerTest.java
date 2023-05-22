package com.iheartjane.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import io.micronaut.http.client.exceptions.HttpClientResponseException;

@MicronautTest
public class CampaignControllerTest {
  @Inject
  @Client("/")
  HttpClient client;

  @Inject
  CampaignController controller;

  @Inject
  CampaignService campaignService;

  private Campaign basicCampaign = new Campaign(
      null,
      1L,
      3L,
      Set.of("iphone5", "5G"),
      1L,
      0.001f
  );


  @AfterEach
  public void clear() {
    campaignService.clear();
  }

  @Test
  public void testCreate() {
    var httpResponse = controller.create(basicCampaign);
    assertEquals(1001, httpResponse.body().campaignId());
  }

  @Test
  public void testCannotAddDuplicateCampaign() {
    var firstResponse = controller.create(basicCampaign);
    var secondResponse = controller.create(basicCampaign);

    assertEquals(HttpStatus.OK, firstResponse.status());
    assertEquals(1001, firstResponse.body().campaignId());
    assertEquals(HttpStatus.BAD_REQUEST, secondResponse.status());
  }

  @Test
  public void testInvalidCampaignBody() {
    Campaign invalidCampaign = new Campaign(
        null,
        -1L,
        -3L,
        Set.of("android"), //empty set
        -1L,
        -0.001f
    );

    var request = HttpRequest.POST("/campaign", invalidCampaign);
    assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(request));

    var httpResponse = controller.create(invalidCampaign);
    assertEquals(HttpStatus.BAD_REQUEST, httpResponse.status());
  }
}
