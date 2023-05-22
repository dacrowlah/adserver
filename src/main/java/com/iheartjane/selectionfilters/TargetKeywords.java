package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.MISSING_TARGETED_KEYWORDS;
import static java.util.Optional.of;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class TargetKeywords implements SelectionFilter {
  private final Optional<FilterReason> REASON = of(MISSING_TARGETED_KEYWORDS);

  @Override
  public Optional<FilterReason> accept(Campaign campaign, AdRequest adRequest) {
    var campaignKeywords = campaign.getTargetKeywords();
    var requestKeywords = adRequest.getKeywords();

    var result = campaignKeywords
        .stream()
        .filter(kw -> requestKeywords.contains(kw))
        .findFirst();

    return result.isEmpty() ? REASON : Optional.empty();
  }
}
