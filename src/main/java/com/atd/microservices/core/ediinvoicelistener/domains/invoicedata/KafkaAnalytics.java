package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import lombok.Data;

@Data
public class KafkaAnalytics {
	
	private String signatureType;
	private KafkaAnalyticsData kafkaAnalyticsData;
}
