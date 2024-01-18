package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.MISSING_TARGETED_KEYWORDS;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

@Singleton
public class TargetKeywords implements SelectionFilter {
  private static Logger logger = getLogger(TargetKeywords.class);
  private final Optional<FilterReason> REASON = of(MISSING_TARGETED_KEYWORDS);

  @Override
  public Optional<FilterReason> accept(Campaign campaign, AdRequest adRequest) {
    var campaignKeywords = campaign.getTargetKeywords();
    var requestKeywords = adRequest.getKeywords();

    var result = campaignKeywords
        .stream()
        .filter(kw -> requestKeywords.contains(kw))
        .findFirst();

    if (result.isEmpty()) {
      logger.warn(CAMPAIGN_ID_FILTERED_FOR, campaign.getCampaignId(), REASON.get());
      return REASON;
    }

    return Optional.empty();
  }
}
