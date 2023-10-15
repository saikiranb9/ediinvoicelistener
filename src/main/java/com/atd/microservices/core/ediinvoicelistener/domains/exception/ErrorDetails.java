package com.atd.microservices.core.ediinvoicelistener.domains.exception;

import com.atd.microservices.core.ediinvoicelistener.domains.deserializer.JsonDateTimeDeserializer;
import com.atd.microservices.core.ediinvoicelistener.domains.serializer.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {

  @JsonDeserialize(using = JsonDateTimeDeserializer.class)
  @JsonSerialize(using = JsonDateTimeSerializer.class)
  private ZonedDateTime timestamp;

  private String message;
  private List<FieldError> fieldErrorsList;
  private String details;
  private String requestPath;
  private String origsysdocumentref;
  private String status;
  private String requestPayLoad;

  public ErrorDetails(ZonedDateTime timestamp, String message,String requestPath,String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
    this.requestPath =requestPath;
  }
  public ErrorDetails(String message, String details,String requestPath) {
    super();
    this.timestamp = ZonedDateTime.now();
    this.message = message;
    this.details = details;
    this.requestPath =requestPath;
  }

  public ErrorDetails(String message, String details,String requestPath,String origsysdocumentref) {
    super();
    this.timestamp = ZonedDateTime.now();
    this.message = message;
    this.details = details;
    this.requestPath =requestPath;
    this.origsysdocumentref = origsysdocumentref;
  }

  @Override
  public String toString() {
    try {

      return new ObjectMapper().writeValueAsString(this);
    } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

}