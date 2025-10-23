package ru.mosit.pahotest.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.mosit.pahotest.InstantConverter;
import ru.mosit.pahotest.data.JsonData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class JsonParser {
    private final Gson gson;
    
    public JsonParser() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .setPrettyPrinting()
                .create();
    }
    
    public List<JsonData> parseJsonFile(String filePath) throws IOException {
        String jsonContent = Files.readString(Paths.get(filePath));
        return gson.fromJson(jsonContent, new TypeToken<List<JsonData>>(){}.getType());
    }
    
    public void printJsonData(List<JsonData> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("❌ Нет данных для отображения");
            return;
        }
        
        System.out.println("📄 === JSON ДАННЫЕ ===");
        System.out.println("📊 Всего записей: " + dataList.size());
        System.out.println("┌" + "─".repeat(80) + "┐");
        
        for (int i = 0; i < dataList.size(); i++) {
            JsonData data = dataList.get(i);
            printDataRecord(i + 1, data);
            if (i < dataList.size() - 1) {
                System.out.println("├" + "─".repeat(80) + "┤");
            }
        }
        
        System.out.println("└" + "─".repeat(80) + "┘");
    }
    
    private void printDataRecord(int index, JsonData data) {
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
