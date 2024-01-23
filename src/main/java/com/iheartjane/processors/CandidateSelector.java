package com.iheartjane.processors;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.selectionfilters.SelectionFilter;
import com.iheartjane.selectionfilters.SelectionFilters;
import com.iheartjane.services.CampaignService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class CandidateSelector {
  private final CampaignService campaignService;
  private List<SelectionFilter> selectionFilters;

  @Inject
  public CandidateSelector(
      CampaignService campaignService,
      SelectionFilters selectionFilters
  ) {
    this.campaignService = campaignService;
    this.selectionFilters = selectionFilters;
  }

  public List<Campaign> accept(AdRequest adRequest) {
    return campaignService
        .getCurrentCampaigns()
        .stream()
        .filter(c -> !isFiltered(c, adRequest))
        .collect(Collectors.toList());
  }

  /*
   * here we can do one of two things, find all reasons that a campaign would be filtered,
   * or just find the first reason it's filtered and not check any subsequent reasons.
   * in a high request volume system it's preferable to only check the first and save compute
   * costs.
   */
  private boolean isFiltered(Campaign campaign, AdRequest adRequest) {
    return selectionFilters
        .stream()
        .flatMap(f -> Stream.of(f.accept(campaign, adRequest)))
        .anyMatch(Optional::isPresent);
  }
}
