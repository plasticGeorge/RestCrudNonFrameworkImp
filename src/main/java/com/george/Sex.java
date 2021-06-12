package com.george;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Sex {
    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE,
    @JsonProperty("transgender")
    TRANSGENDER
}
