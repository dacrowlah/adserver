package com.iheartjane.processors;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@MicronautTest
public class AuctioneerTest {

  @Inject
  Auctioneer auctionProcessor;

  @Test
  public void testSameCpmSameEndTimeLowestIdWins() {
    Campaign campaignOne = getBy(1, 10f, 100l);
    Campaign campaignTwo = getBy(2, 10f, 100l);
    Campaign campaignThree = getBy(3, 10f, 100l);

    var candidates = new ArrayList<>(List.of(campaignOne, campaignThree, campaignTwo));
    var result = auctionProcessor.accept(candidates);

    assertTrue(result.get().getCampaignId() == 1);
  }

  @Test
  public void testSameCpmSecondCampaignHasCloserEndTime() {
    Campaign campaignOne = getBy(1, 10f, 100l);
    Campaign campaignTwo = getBy(2, 10f, 99l);
    Campaign campaignThree = getBy(3, 10f, 100l);

    var candidates = new ArrayList<>(List.of(campaignOne, campaignThree, campaignTwo));
    var result = auctionProcessor.accept(candidates);

    assertTrue(result.get().getCampaignId() == 2);
  }

  @Test
  public void testThirdHasHighestCpmThirdWins() {
    Campaign campaignOne = getBy(1, 10f, 100l);
    Campaign campaignTwo = getBy(2, 10f, 100l);
    Campaign campaignThree = getBy(3, 20f, 100l);

    var candidates = new ArrayList<>(List.of(campaignOne, campaignThree, campaignTwo));
    var auctionWinner = auctionProcessor.accept(candidates);

    assertTrue(auctionWinner.get().getCampaignId() == 3);
  }

  private Campaign getBy(int campaignId, float cpm, long endTime) {
    Campaign c = new Campaign();

    c.setCampaignId(campaignId);
    c.setCpm(cpm);
    c.setEndTimestamp(endTime);

    return c;
  }
}
