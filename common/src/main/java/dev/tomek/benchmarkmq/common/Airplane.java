package dev.tomek.benchmarkmq.common;

public record Airplane(Maker maker, String id) {
    public enum Maker {
        AIRBUS,
        BOEING,
        NORTH_AMERICAN_AVIATION;
    }
}
