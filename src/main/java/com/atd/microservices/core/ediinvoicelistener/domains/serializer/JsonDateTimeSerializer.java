package com.atd.microservices.core.ediinvoicelistener.domains.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
                                                                                     //2020-01-31T02:34:48.152-05:00
	//public static  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public static  DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	
    @Override
    public void serialize(ZonedDateTime dateTime, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        gen.writeString(dateTime.format(DATE_TIME_FORMATTER));
    }

}