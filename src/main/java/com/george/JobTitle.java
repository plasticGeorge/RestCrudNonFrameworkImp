package com.george;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JobTitle {
    @JsonProperty("trainee")
    TRAINEE,
    @JsonProperty("junior")
    JUNIOR,
    @JsonProperty("middle")
    MIDDLE,
    @JsonProperty("senior")
    SENIOR,
    @JsonProperty("team_lead")
    TEAM_LEAD
}
