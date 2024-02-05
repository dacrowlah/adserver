package com.iheartjane.selectionfilters;

import static com.iheartjane.api.SelectionFilter.FilterReason.CURRENT_TIMESTAMP_NOT_IN_RANGE;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.api.SelectionFilter;
import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;
import org.slf4j.Logger;

/**
 * Ensures that the start/end times of a campaign cover the current point in time.
 */
@Singleton
public class StartAndEndTimesActive implements SelectionFilter {
  private static Logger logger = getLogger(StartAndEndTimesActive.class);

  private static final Optional<FilterReason> REASON = of(CURRENT_TIMESTAMP_NOT_IN_RANGE);

  /**
   * For a given campaign, determine if the campaign is currently active.
   * @param campaign
   * @param adRequest
   * @return an Optional filtering reason
   */
  @Override
  public Optional<FilterReason> accept(Campaign campaign, AdRequest adRequest) {
    long currentTimestamp = System.currentTimeMillis() / 1000;
    var startTime = campaign.getStartTimestamp();
    var endTime = campaign.getEndTimestamp();

    if (currentTimestamp >= startTime && currentTimestamp <= endTime) {
      return Optional.empty();
    }

    logger.warn(CAMPAIGN_ID_FILTERED_FOR, campaign.getCampaignId(), CURRENT_TIMESTAMP_NOT_IN_RANGE);
    return REASON;
  }
}
