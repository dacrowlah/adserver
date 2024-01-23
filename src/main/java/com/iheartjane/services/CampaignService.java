package com.iheartjane.services;

import com.iheartjane.models.Campaign;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

@Singleton
public class CampaignService {
  @Getter
  private Set<Campaign> currentCampaigns = ConcurrentHashMap.newKeySet();
  private Map<Integer, Campaign> campaignMap = new ConcurrentHashMap<>();
  private final AtomicInteger campaignIds = new AtomicInteger(1000);

  public boolean exists(Campaign campaign) {
    return currentCampaigns.contains(campaign);
  }

  public void addCampaign(Campaign campaign) {
    int campaignId = campaignIds.incrementAndGet();
    campaign.setCampaignId(campaignId);
    currentCampaigns.add(campaign);
    campaignMap.put(campaignId, campaign);
  }

  /**
   * adding this as a workaround to some issues that have been coming up in unit tests.
   */
  public void clear() {
    currentCampaigns.clear();
    campaignMap.clear();
    campaignIds.set(1000);
  }
}
