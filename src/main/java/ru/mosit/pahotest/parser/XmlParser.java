package ru.mosit.pahotest.parser;

import ru.mosit.pahotest.data.XmlData;
import ru.mosit.pahotest.data.XmlDataList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class XmlParser {
    private final JAXBContext context;
    private final Unmarshaller unmarshaller;
    
    public XmlParser() throws JAXBException {
        this.context = JAXBContext.newInstance(XmlDataList.class);
        this.unmarshaller = context.createUnmarshaller();
    }
    
    public List<XmlData> parseXmlFile(String filePath) throws JAXBException {
        File file = new File(filePath);
        XmlDataList xmlDataList = (XmlDataList) unmarshaller.unmarshal(file);
        return xmlDataList.getDataList();
    }
    
    public void printXmlData(List<XmlData> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("❌ Нет данных для отображения");
            return;
        }
        
        System.out.println("📄 === XML ДАННЫЕ ===");
        System.out.println("📊 Всего записей: " + dataList.size());
        System.out.println("┌" + "─".repeat(80) + "┐");
        
        for (int i = 0; i < dataList.size(); i++) {
            XmlData data = dataList.get(i);
            printDataRecord(i + 1, data);
            if (i < dataList.size() - 1) {
                System.out.println("├" + "─".repeat(80) + "┤");
            }
        }
        
        System.out.println("└" + "─".repeat(80) + "┘");
    }
    
    private void printDataRecord(int index, XmlData data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(new Locale("ru", "RU"));
        
        System.out.printf("│ %-3d │ Освещенность: %-8.2f │ Движение: %-3d │ Температура: %-6.2f°C │ Звук: %-6.2f │%n",
                index,
                data.getIlluminance() != null ? data.getIlluminance() : 0.0f,
                data.getMotion() != null ? data.getMotion() : 0,
                data.getTemperature() != null ? data.getTemperature() : 0.0f,
                data.getSound() != null ? data.getSound() : 0.0f);
        
        System.out.printf("│     │ Дата: %-20s │ Кейс: %-3d │%n",
                data.getDate() != null ? data.getDate().toString() : "Не указано",
                data.getCaseNumber() != null ? data.getCaseNumber() : 0);
    }
}
