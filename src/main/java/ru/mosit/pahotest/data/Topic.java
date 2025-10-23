package ru.mosit.pahotest.data;

import lombok.Getter;

// Enum для хранения топиков и удобной пдписка к ним
@Getter
public enum Topic {
    MOTION("/devices/wb-msw-v3_21/controls/Current Motion"),
    SOUND("/devices/wb-msw-v3_21/controls/Sound Level"),
    ILLUMINANCE("/devices/wb-ms_11/controls/Illuminance"),
    TEMPERATURE("/devices/wb-ms_11/controls/Temperature");


    private final String value;
    Topic(String value) {
        this.value = value;
    }

    public static Topic fromValue(String value) {
        for (final Topic dayOfWeek : values()) {
            if (dayOfWeek.value.equalsIgnoreCase(value)) {
                return dayOfWeek;
            }
        }
        return null;
    }
}
