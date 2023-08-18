package com.company.proxyproject.constants.enums;


import java.util.Objects;

public enum Status {
    ACTIVE, DISABLED, PENDING, MAINTENANCE;

    public static boolean isActive(Status status) {
        return Objects.equals(status, ACTIVE);
    }

    public static boolean isPending(Status status) {
        return Objects.equals(status, PENDING);
    }

    public static boolean isDisabled(Status status) {
        return Objects.equals(status, DISABLED);
    }

    public static boolean isMaintenance(Status status) {
        return Objects.equals(status, MAINTENANCE);
    }
}
