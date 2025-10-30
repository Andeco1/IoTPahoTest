package ru.mosit.pahotest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mosit.pahotest.XmlInstantConverter;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;

// Data-class для хранения информации в XML
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "sensorData")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlData {
    @XmlElement(name = "illuminance")
    private Float illuminance;
    
    @XmlElement(name = "motion")
    private Integer motion;
    
    @XmlElement(name = "temperature")
    private Float temperature;
    
    @XmlElement(name = "sound")
    private Float sound;
    
    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(XmlInstantConverter.class)
    private Instant date;
    
    @XmlElement(name = "caseNumber")
    private Integer caseNumber;

    public XmlData(XmlData another) {
        this.illuminance = another.getIlluminance();
        this.motion = another.getMotion();
        this.temperature = another.getTemperature();
        this.sound = another.getSound();
        this.date = another.getDate();
        this.caseNumber = another.getCaseNumber();
    }
    
    public XmlData(JsonData jsonData) {
        this.illuminance = jsonData.getIlluminance();
        this.motion = jsonData.getMotion();
        this.temperature = jsonData.getTemperature();
        this.sound = jsonData.getSound();
        this.date = jsonData.getDate();
        this.caseNumber = jsonData.getCaseNumber();
    }
}
