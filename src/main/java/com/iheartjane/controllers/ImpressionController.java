package com.iheartjane.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.services.CampaignService;
import com.iheartjane.services.ImpressionService;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;

/**
 * ImpressionController is entrypoint for tracking ad impressions
 */
@Controller
public class ImpressionController {
  private static Logger logger = getLogger(ImpressionController.class);
  private final CampaignService campaignService;
  private final ImpressionService impressionService;

  @Inject
  public ImpressionController(CampaignService campaignService, ImpressionService impressionService) {
    this.campaignService = campaignService;
    this.impressionService = impressionService;
  }

  /**
   * Tracks ad impressions
   * Note: to implement capping by user/device/otherwise specific client, we
   *       would need to add a third (or more?) parameter to this for the client identifier
   *
   * @param campaignId the campaign for this impression
   *
   * @param impressionId a unique identifier to de-duplicate impressions and avoid double counting.
   *                     in a production level system there are different ways to do this, depending
   *                     on what data is available... sometimes it can be a compound value of
   *                     various other pieces of data about the request.
   *
   * @return In the event of a valid impression tracking event happening, an HTTP 200 OK
   * response is sent with a blank body.  In the event of an invalid event happening, a
   * 400 BAD REQUEST response is sent.
   */
  @Get("/impression")
  public HttpResponse impression(@Parameter Integer campaignId, @Parameter String impressionId) {
    if (impressionService.isNotValidImpression(campaignId, impressionId)) {
      logger.warn("Unknown impression signature: {}:{}", campaignId, impressionId);
      return HttpResponse.badRequest();
    }

    impressionService.trackImpression(campaignId, impressionId);
    return HttpResponse.ok();
  }
}
