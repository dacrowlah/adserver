package com.iheartjane.processors;

import static java.util.Collections.sort;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Singleton
public class Auctioneer {
  private final Comparator<Campaign> auctionComparator = Comparator
      .comparing(Campaign::getCpm, descendingFloatComparator)
      .thenComparing(Campaign::getEndTimestamp)
      .thenComparing(Campaign::getCampaignId);


  private static final Comparator<Float> descendingFloatComparator = (o1, o2) -> {
    if (o1.compareTo(o2) == 0) {
      return 0;
    } else if (o1.compareTo(o2) < 0) {
      return 1;
    } else {
      return -1;
    }
  };

  public Optional<Campaign> accept(List<Campaign> candidates) {
    if (candidates.isEmpty()) {
      return Optional.empty();
    }

    sort(candidates, auctionComparator);

    return Optional.of(candidates.get(0));
  }

}
