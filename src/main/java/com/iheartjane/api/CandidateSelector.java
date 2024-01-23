package com.iheartjane.api;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import java.util.List;

public interface CandidateSelector {

  List<Campaign> accept(AdRequest adRequest);
}
