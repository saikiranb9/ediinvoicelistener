package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class InvoiceData {

	private EdiConfigResponse ediConfigResponse;
	
	@JsonProperty("HEADER")
	private JsonNode header;
	
}
