package ru.mosit.pahotest;

import ru.mosit.pahotest.data.JsonData;
import ru.mosit.pahotest.data.XmlData;
import ru.mosit.pahotest.parser.JsonParser;
import ru.mosit.pahotest.parser.XmlParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class DataParser {
    private final JsonParser jsonParser;
    private final XmlParser xmlParser;
    private final Scanner scanner;
    
    public DataParser() throws Exception {
        this.jsonParser = new JsonParser();
        this.xmlParser = new XmlParser();
        this.scanner = new Scanner(System.in);
    }
    
    public static void main(String[] args) {
        try {
            DataParser parser = new DataParser();
            parser.run();
        } catch (Exception e) {
            System.err.println("❌ Ошибка инициализации парсера: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void run() {
        printWelcomeMessage();
        
        while (true) {
            printMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1 -> parseJsonFile();
                case 2 -> parseXmlFile();
                case 3 -> parseBothFiles();
                case 4 -> showFileInfo();
                case 5 -> {
                    System.out.println("👋 До свидания!");
                    return;
                }
                default -> System.out.println("❌ Неверный выбор. Попробуйте снова.");
            }
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Нажмите Enter для продолжения...");
            scanner.nextLine();
        }
    }
    
    private void printWelcomeMessage() {
        System.out.println("🎯 " + "=".repeat(58) + " 🎯");
        System.out.println("│" + " ".repeat(20) + "ПАРСЕР ДАННЫХ MQTT" + " ".repeat(20) + "│");
        System.out.println("│" + " ".repeat(15) + "JSON и XML формат" + " ".repeat(15) + "│");
        System.out.println("🎯 " + "=".repeat(58) + " 🎯");
        System.out.println();
    }
    
    private void printMenu() {
        System.out.println("📋 МЕНЮ:");
        System.out.println("1️⃣  Парсить JSON файл (data.json)");
        System.out.println("2️⃣  Парсить XML файл (data.xml)");
        System.out.println("3️⃣  Парсить оба файла");
        System.out.println("4️⃣  Информация о файлах");
        System.out.println("5️⃣  Выход");
        System.out.print("👉 Выберите опцию (1-5): ");
    }
    
    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void parseJsonFile() {
        System.out.println("\n🔍 Парсинг JSON файла...");
        try {
            if (!fileExists("data.json")) {
                System.out.println("❌ Файл data.json не найден!");
                return;
            }
            
            List<JsonData> data = jsonParser.parseJsonFile("data.json");
            jsonParser.printJsonData(data);
            
        } catch (IOException e) {
            System.out.println("❌ Ошибка чтения JSON файла: " + e.getMessage());
        }
    }
    
    private void parseXmlFile() {
        System.out.println("\n🔍 Парсинг XML файла...");
        try {
            if (!fileExists("data.xml")) {
                System.out.println("❌ Файл data.xml не найден!");
                return;
            }
            
            List<XmlData> data = xmlParser.parseXmlFile("data.xml");
            xmlParser.printXmlData(data);
            
        } catch (Exception e) {
            System.out.println("❌ Ошибка чтения XML файла: " + e.getMessage());
        }
    }
    
    private void parseBothFiles() {
        System.out.println("\n🔍 Парсинг обоих файлов...");
        
        // Парсим JSON
        parseJsonFile();
        System.out.println("\n" + "─".repeat(60));
        
        // Парсим XML
        parseXmlFile();
    }
    
    private void showFileInfo() {
        System.out.println("\n📊 ИНФОРМАЦИЯ О ФАЙЛАХ:");
        System.out.println("┌" + "─".repeat(50) + "┐");
        
        checkFileInfo("data.json", "JSON данные");
        checkFileInfo("data.xml", "XML данные");
        
        System.out.println("└" + "─".repeat(50) + "┘");
    }
    
    private void checkFileInfo(String filename, String description) {
        File file = new File(filename);
        if (file.exists()) {
            try {
                long size = Files.size(Paths.get(filename));
                String lastModified = LocalDateTime.ofInstant(
                    java.time.Instant.ofEpochMilli(file.lastModified()),
                    java.time.ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                
                System.out.printf("│ ✅ %-15s │ %-8s │ %s │%n", 
                    description, 
                    formatFileSize(size),
                    lastModified);
            } catch (IOException e) {
                System.out.printf("│ ❌ %-15s │ Ошибка чтения информации │%n", description);
            }
        } else {
            System.out.printf("│ ❌ %-15s │ Файл не найден │%n", description);
        }
    }
    
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
    
    private boolean fileExists(String filename) {
        return new File(filename).exists();
    }
}
