package ru.mosit.pahotest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

// Класс-обертка для списка XML данных
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "sensorDataList")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDataList {
    
    @XmlElement(name = "sensorData")
    private List<XmlData> dataList = new ArrayList<>();
    
    public void addData(XmlData data) {
        dataList.add(data);
    }
    
    public void addAllData(List<XmlData> data) {
        dataList.addAll(data);
    }
}
