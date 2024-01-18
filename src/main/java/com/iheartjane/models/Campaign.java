package com.iheartjane.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Campaign {

  @EqualsAndHashCode.Exclude
  @JsonProperty("campaign_id")
  private Integer campaignId;

  @EqualsAndHashCode.Include
  @JsonProperty("start_timestamp")
  private long startTimestamp;

  @EqualsAndHashCode.Include
  @JsonProperty("end_timestamp")
  private long endTimestamp;

  @EqualsAndHashCode.Include
  @JsonProperty("target_keywords")
  private Set<String> targetKeywords;

  @EqualsAndHashCode.Include
  @JsonProperty("max_impression")
  private long maxImpression;

  @EqualsAndHashCode.Include
  private float cpm;

  public boolean hasNoKeywords() {
    return targetKeywords == null || targetKeywords.isEmpty();
  }
}
