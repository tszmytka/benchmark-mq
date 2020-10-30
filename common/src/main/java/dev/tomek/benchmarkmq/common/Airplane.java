package dev.tomek.benchmarkmq.common;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value = "EQ_UNUSUAL", justification = "It complains about auto-generated code")
public record Airplane(Maker maker, String id, long dispatchNanos) {
    public enum Maker {
        AIRBUS,
        BOEING,
        NORTH_AMERICAN_AVIATION;
    }
}
