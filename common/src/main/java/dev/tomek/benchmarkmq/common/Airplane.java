package dev.tomek.benchmarkmq.common;

public record Airplane(Maker maker, String id, long dispatchNanos) {
    public enum Maker {
        AIRBUS,
        BOEING,
        NORTH_AMERICAN_AVIATION;
    }
}
