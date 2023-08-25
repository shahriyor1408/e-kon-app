package com.company.proxyproject.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SensorDirection {
    IN("UPLINK"),
    OUT("DOWNLINK");
    private final String value;
}
