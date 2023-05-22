package com.iheartjane.services;

import static org.slf4j.LoggerFactory.getLogger;

import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

@Singleton
public class ImpressionService {
  private static Logger logger = getLogger(ImpressionService.class);
  private Map<Integer, AtomicInteger> campaignCapping = new ConcurrentHashMap<>();
  private Set<String> seenImpressionIds = ConcurrentHashMap.newKeySet();

  public void trackImpression(int campaignId, String impressionId) {
    if (seenImpressionIds.contains(impressionId)) {
      /*
       * don't want to double count the impressions - this would cause advertisers to be double+
       * charged.
       */
      logger.warn("Previously seen impression id, discarding {}", impressionId);
      return;
    }

    seenImpressionIds.add(impressionId);
    campaignCapping
        .computeIfAbsent(campaignId, c -> new AtomicInteger(0))
        .incrementAndGet();
  }

  public int getCampaignImpressions(Integer campaignId) {
    return campaignCapping
        .computeIfAbsent(campaignId, c -> new AtomicInteger(0))
        .get();
  }

  /**
   * this is to handle issues that arise during unit testing - the DI framework doesn't reinitialize
   * previously injected values to the test classes.
   */
  public void clear() {
    campaignCapping.clear();
    seenImpressionIds.clear();
  }
}
