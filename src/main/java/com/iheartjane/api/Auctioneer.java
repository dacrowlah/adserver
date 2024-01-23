package com.iheartjane.api;

import com.iheartjane.models.Campaign;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface Auctioneer {
  Comparator<Float> DESCENDING_FLOAT_COMPARATOR = (o1, o2) -> {
    if (o1.compareTo(o2) == 0) {
      return 0;
    } else if (o1.compareTo(o2) < 0) {
      return 1;
    } else {
      return -1;
    }
  };

  Optional<Campaign> accept(List<Campaign> candidates);
}
