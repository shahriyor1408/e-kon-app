package com.company.proxyproject.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SensorDirection {
    IN("UPLINK"),
    OUT("DOWNLINK");

    private final String value;

    public static SensorDirection getByValue(String value) {
        switch (value) {
            default -> {
                return IN;
            }
            case "DOWNLINK" -> {
                return OUT;
            }
        }
    }
}
