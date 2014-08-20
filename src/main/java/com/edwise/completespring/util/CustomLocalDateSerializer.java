package com.edwise.completespring.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by user EAnton on 04/04/2014.
 */
public class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeString(DATE_TIME_FORMATTER.print(value));
    }
}
