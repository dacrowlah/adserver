package com.iheartjane.api;

import com.iheartjane.models.AdResponse;
import com.iheartjane.models.Campaign;

public interface AdResponder {

  AdResponse accept(Campaign campaign);
}
