package com.iheartjane.controllers;

import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.AdResponse;
import com.iheartjane.api.AdResponder;
import com.iheartjane.api.Auctioneer;
import com.iheartjane.api.CandidateSelector;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.slf4j.Logger;

/**
 * AdDecisionController is the entrypoint for ad requests
 */
@Controller
public class AdDecisionController {
  private static Logger logger = getLogger(AdDecisionController.class);
  private static final String NO_WINNER_FOR_REQUEST = "No winner for request: {}";
  private final CandidateSelector candidateSelector;
  private final Auctioneer auctioneer;
  private final AdResponder responder;

  @Inject
  public AdDecisionController(
      CandidateSelector candidateSelector,
      Auctioneer auctioneer,
      AdResponder responder
  ) {
    this.candidateSelector = candidateSelector;
    this.auctioneer = auctioneer;
    this.responder = responder;
  }

  /**
   * For a given json ad request formatted as {"keywords": ["key","word","list","here"]}
   * identify all active campaigns that target the provided keywords
   * @param adRequest a json formatted request. the micronaut framework handles serialization and
   *                   deserialization transparently.
   *
   * @return an AdResponse object, formatted as json by the time it gets to the client. If there is
   *         no ad for a given request, an HTTP 200 OK, with a blank body is returned to the client.
   *         Note: Some external partners if there are any, may require an HTTP 204 NO CONTENT
   *         instead of an OK with blank body.
   */
  @Post("/addecision")
  public HttpResponse<AdResponse> getAd(@Body AdRequest adRequest) {
    var candidates = candidateSelector.accept(adRequest);
    var winner = auctioneer.accept(candidates);

    if (winner.isEmpty()) {
      // if a partner requires a 204 NO CONTENT status, this would just become:
      // return HttpResponse.noContent();
      logger.warn(NO_WINNER_FOR_REQUEST, adRequest);
      return HttpResponse.ok();
    }

    var adResponse = responder.accept(winner.get());

    return HttpResponse.ok(adResponse);
  }
}
