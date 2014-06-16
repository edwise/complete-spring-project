package com.edwise.completespring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomLocalDateSerializerTest {

    @Test
    public void testSerialize() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LocalDate date = new LocalDate(2010, 11, 12);

        assertEquals("{\"date\":\"2010-11-12\"}", mapper.writeValueAsString(new SampleTest(date)));
    }

    class SampleTest {
        @JsonSerialize(using = CustomLocalDateSerializer.class)
        LocalDate date;

        public SampleTest(LocalDate date) {
            this.date = date;
        }
    }
}