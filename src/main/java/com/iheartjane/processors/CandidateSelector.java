package com.iheartjane.processors;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import com.iheartjane.selectionfilters.ImpressionCap;
import com.iheartjane.selectionfilters.SelectionFilter;
import com.iheartjane.selectionfilters.StartAndEndTimesActive;
import com.iheartjane.selectionfilters.TargetKeywords;
import com.iheartjane.services.CampaignService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class CandidateSelector {
  private final CampaignService campaignService;
  private List<SelectionFilter> selectionFilters = new LinkedList<>();

  @Inject
  public CandidateSelector(
      CampaignService campaignService,
      StartAndEndTimesActive startAndEndTimesActive,
      TargetKeywords keywordsFilter,
      ImpressionCap impressionCap
  ) {
    this.campaignService = campaignService;

    /*
     * using a LinkedList here to control order in which filters run. there are opportunities here
     * to optimize the order of these... if you can identify the ones that are most frequently the
     * cause for a candidate to be removed, you run that filter first, then it eliminates the need
     * for any subsequent filters to be run.
     */
    selectionFilters.add(startAndEndTimesActive);
    selectionFilters.add(keywordsFilter);
    selectionFilters.add(impressionCap);
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
