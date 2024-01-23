package com.iheartjane.selectionfilters;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.LinkedList;

/**
 * SelectionFilters is a resource bundle of the current selection filters available.
 *
 * SelectionFilters uses a LinkedList to control order in which filters run. There are
 * opportunities here to optimize the order of these... if you can identify the ones that are
 * most frequently the cause for a candidate to be removed, you run that filter first, then
 * it eliminates the need for any subsequent filters to be run.
 */
@Singleton
public class SelectionFilters extends LinkedList<SelectionFilter> {
  @Inject
  public SelectionFilters(
      StartAndEndTimesActive startAndEndTimesActive,
      TargetKeywords keywordsFilter,
      ImpressionCap impressionCap
  ) {
    add(startAndEndTimesActive);
    add(keywordsFilter);
    add(impressionCap);
  }
}
