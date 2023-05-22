package com.iheartjane.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.Campaign;
import com.iheartjane.models.CampaignCreationResponse;
import com.iheartjane.services.CampaignService;
import com.iheartjane.processors.CampaignValidationProcessor;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;
import org.slf4j.Logger;

/**
 * CampaignController is the api entrypoint for creating new campaigns (and in a production level
 * system, updating, managing, etc)
 */
@Controller
public class CampaignController {
  private final CampaignService campaignService;
  private final CampaignValidationProcessor validationProcessor;

  @Inject
  public CampaignController(
      CampaignService campaignService,
      CampaignValidationProcessor validationProcessor
  ) {
    this.campaignService = campaignService;
    this.validationProcessor = validationProcessor;
  }

  /**
   * For a given json formatted campaign ensures that all validation criteria are met, and avoids
   * duplicated campaigns.
   *
   * @param campaign
   * @return for a successful campaign creation, a json message with the created campaign id is
   * returned.  for an unsuccessful requet, an HTTP 400 BAD REQUEST is returned with an empty body
   */
  @Post("/campaign")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public HttpResponse<CampaignCreationResponse> create(@Body Campaign campaign) {

    var validationErrors = validationProcessor.accept(campaign);

    CampaignCreationResponse campaignCreationResponse;
    if (!validationErrors.isEmpty()) {
      /*
       * the requirements specifically say to return an empty 400 response here, but one could
       * alternatively return a more descriptive body and include the failure reason(s).
       *
       * An example of where this would be useful is to display descriptive error messages in the
       * campaign admin console.  This could be accomplished by adding additional constructor
       * parameter(s) to CampaignCreationResponse like a list of reasons, possibly a boolean success
       * flag.
       *
       *   campaignCreationResponse = new CampaignCreationResponse(null, validationErrors);
       *   return HttpResponse.badRequest(campaignCreationResponse);
       */
      return HttpResponse.badRequest();
    }

    campaignService.addCampaign(campaign);

    campaignCreationResponse = new CampaignCreationResponse(campaign.getCampaignId());
    return HttpResponse.ok(campaignCreationResponse);
  }
}
