package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdiData {

	private String id;
	private String uuid;
	private String traceId;
	private Object rawData; //only send on consumer
	private Object processedData; //send on consumer and producer
	private String lastProcessStage; //service name
	private String errorMessage; // send on error with error message
	private String status; //2XX if error is null, 5XX if error message populated
	private String sourceTopic; //always consumer topic
	private String type; //Call EDI Config
	private String version; 
	private String sendercode;
	private String receivercode;
	private String standard;
	private String customerPO;
	private String creationDateTime; //populate only on consumer
	private String lastUpdationDateTime; //populate on consumer, error, and producer
}
