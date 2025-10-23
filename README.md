# PahoTest - MQTT Data Logger

Проект для получения данных с MQTT брокера и сохранения их в форматах JSON и XML.

## Возможности

- Подключение к MQTT брокеру
- Получение данных с датчиков:
  - Освещенность (Illuminance)
  - Движение (Motion) 
  - Температура (Temperature)
  - Звук (Sound)
- Сохранение данных в двух форматах:
  - **JSON** - файл `data.json`
  - **XML** - файл `data.xml`

## Структура проекта

```
src/main/java/ru/mosit/pahotest/
├── PahoTest.java              # Основной класс с MQTT клиентом
├── TestDataFormats.java       # Тестовый класс для демонстрации форматов
├── InstantConverter.java      # Конвертер для JSON сериализации Instant
├── XmlInstantConverter.java   # Конвертер для XML сериализации Instant
└── data/
    ├── JsonData.java          # Класс данных для JSON
    ├── XmlData.java           # Класс данных для XML
    ├── XmlDataList.java       # Обертка для списка XML данных
    └── Topic.java             # Enum с топиками MQTT
```

## Зависимости

- **Eclipse Paho MQTT Client** - для работы с MQTT
- **Gson** - для JSON сериализации
- **JAXB** - для XML сериализации
- **Lombok** - для упрощения кода

## Использование

### 1. Сбор данных с MQTT
1. Запустите MQTT брокер на `192.168.1.12:1883`
2. Запустите сбор данных:
   ```bash
   ./gradlew run -PmainClass=ru.mosit.pahotest.PahoTest
   ```
3. Данные будут сохраняться в файлы `data.json` и `data.xml`

### 2. Парсинг данных
Запустите интерактивный парсер:
```bash
./gradlew run -PmainClass=ru.mosit.pahotest.DataParser
```

### 3. Тестирование
Для тестирования парсера с тестовыми данными:
```bash
./gradlew run -PmainClass=ru.mosit.pahotest.TestParser
```

## Возможности парсера

- 📄 **Чтение JSON файлов** - парсинг и красивое отображение данных из `data.json`
- 📄 **Чтение XML файлов** - парсинг и красивое отображение данных из `data.xml`
- 📊 **Информация о файлах** - размер, дата изменения, статус файлов
- 🎨 **Красивый интерфейс** - табличное отображение данных с эмодзи и рамками
- ⚡ **Интерактивное меню** - удобная навигация по функциям

## Формат данных

### JSON
```json
[
  {
    "illuminance": 25.5,
    "motion": 1,
    "temperature": 22.3,
    "sound": 45.2,
    "date": "2024-01-15T10:30:00Z",
    "caseNumber": 1
  }
]
```

### XML
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<sensorDataList>
    <sensorData>
        <illuminance>25.5</illuminance>
        <motion>1</motion>
        <temperature>22.3</temperature>
        <sound>45.2</sound>
        <date>2024-01-15T10:30:00Z</date>
        <caseNumber>1</caseNumber>
    </sensorData>
</sensorDataList>
```
