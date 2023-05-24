package com.iheartjane.controllers;

import static com.iheartjane.fixtures.Campaigns.TARGET_KEYWORDS;
import static com.iheartjane.fixtures.Campaigns.validCampaign;
import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.OK;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.ImpressionSignature;
import com.iheartjane.services.CampaignService;
import com.iheartjane.services.ImpressionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
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

    var signature = new ImpressionSignature(
        adResponse.getCampaignId(),
        impressionId.get()
    );

    var response = impressionController.impression(signature);

    assertEquals(OK, response.status());
  }

  @Test
  public void testInvalidImpression() {
    var fakeCampaignId = 2;
    var fakeSignature = new ImpressionSignature(fakeCampaignId, fakeUUID);
    var response = impressionController.impression(fakeSignature);

    assertEquals(BAD_REQUEST, response.status());
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

    var signature = new ImpressionSignature(adResponse.getCampaignId(), impressionId.get());
    var response = impressionController.impression(signature);

    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
    assertEquals(OK, response.status());

    var fakeSignature = new ImpressionSignature(adResponse.getCampaignId(), fakeUUID);
    impressionController.impression(fakeSignature);
    assertEquals(1, impressionService.getCampaignImpressions(adResponse.getCampaignId()));
  }

  @Test
  public void testUrlParamPojoBindingSuccess() {
    var sig = new ImpressionSignature(1001, "fake-impression-id2");
    impressionService.recordSentImpression(sig);

    var requestUrl = format(
        "/impression?campaignId=%s&impressionId=%s",
        sig.campaignId(),
        sig.impressionId()
    );

    var request = HttpRequest.GET(requestUrl);
    var response = client.toBlocking().exchange(request);

    assertEquals(OK, response.status());
  }

  @Test
  public void testUrlParamPojoBindingFailure() {
    var sig = new ImpressionSignature(1001, "fake-impression-id2");

    var requestUrl = format(
        "/impression?campaignId=%s&impressionId=%s",
        sig.campaignId(),
        sig.impressionId()
    );

    var request = HttpRequest.GET(requestUrl);
    assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(request));
  }

  @Test
  public void testImpressionSignatureSuccessReturnStatus() {
    var sig = new ImpressionSignature(1001, "fake-impression-id2");
    impressionService.recordSentImpression(sig);

    var response = impressionController.impression(sig);

    assertEquals(OK, response.status());
  }

  @Test
  public void testImpressionSignatureFailureReturnStatus() {
    var sig = new ImpressionSignature(1001, "fake-impression-id2");
    var response = impressionController.impression(sig);

    assertEquals(BAD_REQUEST, response.status());
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
