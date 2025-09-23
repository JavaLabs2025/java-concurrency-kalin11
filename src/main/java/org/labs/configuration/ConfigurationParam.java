package org.labs.configuration;

import lombok.Getter;

@Getter
public enum ConfigurationParam {
    PROGRAMMERS_COUNT("programmers_count"),
    EAT_COUNT("eat_count"),
    WAITERS_COUNT("waiters_count"),
    TIMEOUT_MS("timeout_ms"),
    ;

    private final String value;

    ConfigurationParam(String value) {
        this.value = value;
    }

    public static ConfigurationParam fromValue(String value) {
        for (ConfigurationParam param : values()) {
            if (param.value.equals(value)) {
                return param;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}
