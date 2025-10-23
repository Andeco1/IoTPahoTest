package ru.mosit.pahotest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

// Data-class для хранения информации в JSON
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private Float illuminance;
    private Integer motion;
    private Float temperature;
    private Float sound;
    private Instant date;
    private Integer caseNumber;

    public JsonData(JsonData another) {
        this.illuminance = another.getIlluminance();
        this.motion = another.getMotion();
        this.temperature = another.getTemperature();
        this.sound = another.getSound();
        this.date = another.getDate();
        this.caseNumber = another.getCaseNumber();
    }
}
