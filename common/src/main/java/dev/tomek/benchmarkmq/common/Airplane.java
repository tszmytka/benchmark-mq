package dev.tomek.benchmarkmq.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Airplane(@JsonProperty("maker") Maker maker, @JsonProperty("id") String id, @JsonProperty("dispatchNanos") long dispatchNanos) {
    // todo inject ObjectMapper from jackson-annotations:2.12.0-rc1 to not have to use @JsonProperty
    public enum Maker {
        AIRBUS,
        BOEING,
        NORTH_AMERICAN_AVIATION;
    }
}
