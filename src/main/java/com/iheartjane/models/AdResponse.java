package com.iheartjane.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdResponse {
  @JsonProperty("campaign_id")
  private Integer campaignId;

  @JsonProperty("impression_url")
  private String impressionUrl;
}
