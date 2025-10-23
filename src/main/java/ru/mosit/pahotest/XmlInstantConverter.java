package ru.mosit.pahotest;

import javax.xml.bind.annotation.adapters.XmlAdapter;
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
