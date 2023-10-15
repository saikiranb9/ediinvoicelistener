package com.atd.microservices.core.ediinvoicelistener.domains.deserializer;

import com.atd.microservices.core.ediinvoicelistener.domains.serializer.JsonDateTimeSerializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZonedDateTime;
@Slf4j
public class JsonDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {


    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return ZonedDateTime.parse(jsonParser.getText(), JsonDateTimeSerializer.DATE_TIME_FORMATTER);
    }
}
