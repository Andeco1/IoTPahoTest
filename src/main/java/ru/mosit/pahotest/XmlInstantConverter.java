package ru.mosit.pahotest;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Instant;

// Конвертер для сериализации Instant в XML
public class XmlInstantConverter extends XmlAdapter<String, Instant> {
    
    @Override
    public Instant unmarshal(String v) throws Exception {
        return v == null ? null : Instant.parse(v);
    }

    @Override
    public String marshal(Instant v) throws Exception {
        return v == null ? null : v.toString();
    }
}
