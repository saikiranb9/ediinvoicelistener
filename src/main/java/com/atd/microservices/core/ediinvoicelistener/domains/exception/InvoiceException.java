package com.atd.microservices.core.ediinvoicelistener.domains.exception;

import com.atd.microservices.core.ediinvoicelistener.domains.deserializer.JsonDateTimeDeserializer;
import com.atd.microservices.core.ediinvoicelistener.domains.serializer.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class InvoiceException extends RuntimeException{

    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private ZonedDateTime timestamp;
    private String message;

    public InvoiceException(Throwable e) {
        super(e);
        this.timestamp = ZonedDateTime.now();
    }

    public InvoiceException(String message) {
        super(message);
        this.message = message;
        this.timestamp = ZonedDateTime.now();
    }

    public InvoiceException(Throwable exceptions, String message) {
        super(exceptions);
        this.message = message;
        this.timestamp = ZonedDateTime.now();
    }
}
