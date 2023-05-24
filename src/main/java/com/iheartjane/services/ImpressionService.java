package com.iheartjane.services;

import static org.slf4j.LoggerFactory.getLogger;

import com.iheartjane.models.ImpressionSignature;
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
  private Set<ImpressionSignature> seenImpressionSignatures = ConcurrentHashMap.newKeySet();
  private Set<ImpressionSignature> sentImpressionSignatures = ConcurrentHashMap.newKeySet();

  public void trackImpression(ImpressionSignature signature) {
    if (seenImpressionSignatures.contains(signature)) {
      /*
       * don't want to double count the impressions - this would cause advertisers to be double+
       * charged.
       */
      logger.warn("Previously seen impression, discarding {}", signature);
      return;
    }

    seenImpressionSignatures.add(signature);
    campaignCapping
        .computeIfAbsent(signature.campaignId(), c -> new AtomicInteger(0))
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
    seenImpressionSignatures.clear();
  }

  public void recordSentImpression(ImpressionSignature signature) {
    sentImpressionSignatures.add(signature);
  }

  public boolean isNotValidImpression(ImpressionSignature signature) {
    return !sentImpressionSignatures.contains(signature);
  }
}
