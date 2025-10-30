package ru.mosit.pahotest.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import ru.mosit.pahotest.data.XmlData;
import ru.mosit.pahotest.data.XmlDataList;

import java.io.File;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class DataParser {
    public static void main(String[] args) {


        String path = "data.xml";
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Файл не найден: " + path);
            return;
        }

        try {
            List<XmlData> dataList;

            if (path.endsWith(".json")) {
                dataList = parseJson(file);
                System.out.println("Формат файла: JSON\n");
            } else if (path.endsWith(".xml")) {
                dataList = parseXml(file);
                System.out.println("Формат файла: XML\n");
            } else {
                System.out.println("Неизвестный формат файла. Поддерживаются .json и .xml");
                return;
            }

            printData(dataList);

        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<XmlData> parseJson(File file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(file, new TypeReference<>() {});
    }

    private static List<XmlData> parseXml(File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(XmlDataList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        XmlDataList list = (XmlDataList) unmarshaller.unmarshal(file);
        return list.getDataList();
    }

    private static void printData(List<XmlData> dataList) {
        DecimalFormat df = new DecimalFormat("0.00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss")
                .withLocale(new Locale("ru"))
                .withZone(ZoneId.systemDefault());
        int i = 1;
        for (XmlData d : dataList) {
            System.out.println("Запись №" + i++);
            System.out.println("Дата и время: " + formatter.format(d.getDate()));
            System.out.println("Номер кейса: " + d.getCaseNumber());
            System.out.println("Освещённость: " + df.format(d.getIlluminance()) + " лк");
            System.out.println("Движение: " + d.getMotion() + " ед.");
            System.out.println("Температура: " + df.format(d.getTemperature()) + " °C");
            System.out.println("Звук: " + df.format(d.getSound()) + " дБ");
            System.out.println();
        }

        System.out.println("Всего записей: " + dataList.size());
    }

}
