package com.iheartjane.fixtures;

import com.iheartjane.models.Campaign;
import java.util.Set;

public class Campaigns {
  public static final Set<String> TARGET_KEYWORDS = Set.of("iphone");

  public static Campaign validCampaign() {
    return new Campaign(
        null,
        currentTimestamp() - 100,
        currentTimestamp() + 100,
        TARGET_KEYWORDS,
        1L,
        0.001f
    );
  }

  private static long currentTimestamp() {
    return System.currentTimeMillis() / 1000;
  }
}
