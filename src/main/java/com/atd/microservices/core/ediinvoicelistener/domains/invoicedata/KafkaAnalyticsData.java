package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import lombok.Data;

@Data
public class KafkaAnalyticsData {

	private String source;
	private String target;
	private String type;
	private String status;
	private String payload;
	private String traceId;
	private String timestamp;
	private Boolean isSecure;
	private String requestContentType;
}
