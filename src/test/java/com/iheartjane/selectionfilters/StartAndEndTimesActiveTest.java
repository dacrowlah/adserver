package com.iheartjane.selectionfilters;

import static com.iheartjane.selectionfilters.SelectionFilter.FilterReason.CURRENT_TIMESTAMP_NOT_IN_RANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iheartjane.models.AdRequest;
import com.iheartjane.models.Campaign;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class StartAndEndTimesActiveTest {
  @Inject
  StartAndEndTimesActive filter;

  @Test
  public void testTimestampInRangeSuccess() {
    AdRequest adRequest = new AdRequest();
    long currentEpoch = System.currentTimeMillis() / 1000;
    long start = currentEpoch - 10;
    long end = currentEpoch + 10;
    Campaign c = new Campaign();
    c.setStartTimestamp(start);
    c.setEndTimestamp(end);
    var result = filter.accept(c, adRequest);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testTimestampNotInRangeFailure() {
    AdRequest adRequest = new AdRequest();
    long start = 1;
    long end = 2;
    Campaign c = new Campaign();
    c.setStartTimestamp(start);
    c.setEndTimestamp(end);
    var result = filter.accept(c, adRequest);
    assertFalse(result.isEmpty());
    assertEquals(CURRENT_TIMESTAMP_NOT_IN_RANGE, result.get());
  }
}
