package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.CURRENT_TIMESTAMP_NOT_IN_RANGE;
import static java.util.Optional.of;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Optional;

/**
 * Ensures that the start/end times of a campaign cover the current point in time.
 */
@Singleton
public class StartAndEndTimesActive implements SelectionFilter {

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

    return REASON;
  }
}
