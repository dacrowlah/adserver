package com.iheartjane.api;

import com.iheartjane.models.AdResponse;
import com.iheartjane.models.Campaign;

public interface AdResponder {
  String IMPRESSION_URL_FORMAT = "http://localhost:8000/impression?campaignId=%s&impressionId=%s";

  AdResponse accept(Campaign campaign);
}
