package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection = "#{@environment.getProperty('backlog.collection')}")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BacklogRecord {

	@Field("payload")
	private String payload;
	@Field("trxNumber")
	private String trxNumber;

}
