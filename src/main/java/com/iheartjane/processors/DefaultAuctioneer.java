package com.iheartjane.processors;

import static java.util.Collections.sort;

import com.iheartjane.api.Auctioneer;
import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Singleton
public class DefaultAuctioneer implements Auctioneer {
  private static final Comparator<Campaign> AUCTION_COMPARATOR = Comparator
      .comparing(Campaign::getCpm, DESCENDING_FLOAT_COMPARATOR)
      .thenComparing(Campaign::getEndTimestamp)
      .thenComparing(Campaign::getCampaignId);

  @Override
  public Optional<Campaign> accept(List<Campaign> candidates) {
    if (candidates.isEmpty()) {
      return Optional.empty();
    }

    sort(candidates, AUCTION_COMPARATOR);

    return Optional.of(candidates.get(0));
  }
}
