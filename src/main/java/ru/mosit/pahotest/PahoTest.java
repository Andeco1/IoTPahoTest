package ru.mosit.pahotest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.paho.client.mqttv3.*;
import ru.mosit.pahotest.data.JsonData;
import ru.mosit.pahotest.data.Topic;
import ru.mosit.pahotest.data.XmlData;
import ru.mosit.pahotest.data.XmlDataList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PahoTest {
    // Параметры подключения к брокеру
    static String host = "192.168.1.12";
    static Integer port = 1883;
    static List<JsonData> jsonDataList = new ArrayList<>();
    static List<XmlData> xmlDataList = new ArrayList<>();

    public static void main(String[] args) throws MqttException {
        // Создание MQTT клиента для подключения
        MqttClient client = new MqttClient(
                String.format("tcp://%s:%d", host, port),
                MqttClient.generateClientId());

        // Создание класса для формирования JSON
        var g = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantConverter()).create();
        // Объект для хранения данных
        var jsonData = new JsonData(0.0F, 0, 0.0F, 0.0F, null,0);
        var xmlData = new XmlData(0.0F, 0, 0.0F, 0.0F, null,0);

        // Создание колбэков для обработки событий, возникающих на клиенте
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("client lost connection " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                var payload = new String(message.getPayload());
                System.out.println(topic + ": " + payload);

                var param = Topic.fromValue(topic);

                if (param != null)
                    switch (param) {
                        case ILLUMINANCE -> {
                            jsonData.setIlluminance(Float.valueOf(payload));
                            xmlData.setIlluminance(Float.valueOf(payload));
                        }
                        case MOTION -> {
                            jsonData.setMotion(Integer.valueOf(payload));
                            xmlData.setMotion(Integer.valueOf(payload));
                        }
                        case TEMPERATURE -> {
                            jsonData.setTemperature(Float.valueOf(payload));
                            xmlData.setTemperature(Float.valueOf(payload));
                        }
                        case SOUND -> {
                            jsonData.setSound(Float.valueOf(payload));
                            xmlData.setSound(Float.valueOf(payload));
                        }
                    }
                else
                    System.out.println("Not known topic");
                
                jsonData.setDate(Instant.now());
                jsonData.setCaseNumber(Integer.valueOf(host.substring(10)));
                xmlData.setDate(Instant.now());
                xmlData.setCaseNumber(Integer.valueOf(host.substring(10)));
                
                jsonDataList.add(new JsonData(jsonData));
                xmlDataList.add(new XmlData(xmlData));
                
                // Сохранение в JSON
                writeUsingFiles(g.toJson(jsonDataList), "data.json");
                // Сохранение в XML
                writeXmlToFile(xmlDataList, "data.xml");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("delivery complete " + token);
            }
        });

        client.connect();

        // Подписывание на топики
        for (var topic : Topic.values())
            client.subscribe(topic.getValue(), 1);
    }

    private static void writeUsingFiles(String data, String filename) {
        try {
            Files.write(Paths.get(filename), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void writeXmlToFile(List<XmlData> dataList, String filename) {
        try {
            XmlDataList xmlDataList = new XmlDataList();
            xmlDataList.addAllData(dataList);
            
            JAXBContext context = JAXBContext.newInstance(XmlDataList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(xmlDataList, writer);
            
            Files.write(Paths.get(filename), writer.toString().getBytes("UTF-8"));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }
}
