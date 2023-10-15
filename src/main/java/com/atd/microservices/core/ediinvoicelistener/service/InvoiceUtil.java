package com.atd.microservices.core.ediinvoicelistener.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.CompleteInvoice;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiConfigResponse;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiData;
import com.fasterxml.jackson.databind.JsonNode;

import brave.Tracer;

@Component
public class InvoiceUtil {

	@Autowired
	private Tracer tracer;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${kafka.topic.outbound}")
	private String kafkaTopicOutbound;

	public String getTraceId() {
		String traceId = null;
		if (tracer != null) {
			traceId = tracer.currentSpan().context().traceIdString();
		}
		return traceId;
	}
	
	public EdiData mapConsumerEdiData(JsonNode data, JsonNode payload, EdiConfigResponse ediConfig, String trxNumber) {
		EdiData ediData = new EdiData();
		ediData.setUuid(ediConfig.getUuid());
		ediData.setTraceId(getTraceId());
		ediData.setRawData(data);
		ediData.setProcessedData(payload);
		ediData.setLastProcessStage(applicationName);
		ediData.setStatus("2XX");
		ediData.setSourceTopic(kafkaTopicOutbound);
		ediData.setType("810");
		ediData.setVersion(ediConfig.getVersion810());
		ediData.setSendercode(ediConfig.getReceiverCode());
		ediData.setReceivercode(ediConfig.getSenderCode());
		ediData.setStandard(ediConfig.getStandard810()); 
		ediData.setCustomerPO(trxNumber);
		ediData.setCreationDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString()+"Z");
		ediData.setLastUpdationDateTime(ediData.getCreationDateTime());
		return ediData;
	}
	
	public EdiData mapProducerEdiData(CompleteInvoice message, EdiConfigResponse ediConfig, String trxNumber) {
		EdiData ediData = new EdiData();
		ediData.setUuid(ediConfig.getUuid());
		ediData.setTraceId(getTraceId());
		ediData.setProcessedData(message);
		ediData.setLastProcessStage(applicationName);
		ediData.setStatus("2xx");
		ediData.setSourceTopic(kafkaTopicOutbound);
		ediData.setType("810");
		ediData.setVersion(ediConfig.getVersion810());
		ediData.setSendercode(ediConfig.getReceiverCode());
		ediData.setReceivercode(ediConfig.getSenderCode());
		ediData.setStandard(ediConfig.getStandard810()); 
		ediData.setCustomerPO(trxNumber);
		ediData.setLastUpdationDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString()+"Z");
		return ediData;
	}
	
	public EdiData mapErrorEdiData(String errorMessage, EdiConfigResponse ediConfig, String trxNumber) {
		EdiData ediData = new EdiData();
		ediData.setUuid(ediConfig.getUuid());
		ediData.setTraceId(getTraceId());
		ediData.setErrorMessage(errorMessage);
		ediData.setLastProcessStage(applicationName);
		ediData.setStatus("5xx");
		ediData.setSourceTopic(kafkaTopicOutbound);
		ediData.setType("810");
		ediData.setVersion(ediConfig.getVersion810());
		ediData.setSendercode(ediConfig.getReceiverCode());
		ediData.setReceivercode(ediConfig.getSenderCode());
		ediData.setStandard(ediConfig.getStandard810()); 
		ediData.setCustomerPO(trxNumber);
		ediData.setLastUpdationDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString()+"Z");
		return ediData;
	}
}
