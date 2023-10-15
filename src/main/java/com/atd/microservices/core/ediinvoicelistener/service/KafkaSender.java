package com.atd.microservices.core.ediinvoicelistener.service;

import static com.atd.microservices.core.ediinvoicelistener.config.AppConstants.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.CompleteInvoice;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.KafkaAnalytics;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.KafkaAnalyticsData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaSender {
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${kafka.topic.outbound}")
	private String kafkaTopicOutbound;
	
	@Value("${kafka.topic.inbound}")
	private String kafkaTopicInbound;
	
	@Autowired
	InvoiceUtil invoiceUtil;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	/**
	 * Submit to Invoice Inbound Topic
	 * @param topicName
	 * @param kafkaMessage
	 */
	public void submitInvoiceToKafka(final String topicName, final CompleteInvoice kafkaMessage) {
		this.kafkaTemplate.send(topicName, kafkaMessage);
		log.info("Submit order to kafka Topic Completed.. :{}", topicName);
	}
	
	/**
	 * Submit to Kafka Analytics to Kafka Topic
	 * @param topicName
	 * @param kafkaMessage
	 */
	public void submitAnalyticsToKafka(final String topicName, final KafkaAnalytics kafkaMessage) {
		this.kafkaTemplate.send(topicName, kafkaMessage);
		log.info("Submit order to kafka Topic Completed.. :{}", topicName);
	}
	
	/**
	 * Map Consumer Kafka Message for EDI API Gateway Analytics
	 * @param invoiceHeaderData
	 * @return
	 */
	public KafkaAnalytics mapConsumerKafkaAnalyticsMessage(JsonNode invoiceHeaderData) {
		KafkaAnalytics kafkaAnalytics = new KafkaAnalytics();
		kafkaAnalytics.setSignatureType(APIGATEWAY_ROUTES);
		KafkaAnalyticsData data = new KafkaAnalyticsData();
		data.setSource(kafkaTopicOutbound);
		data.setTarget(applicationName);
		data.setType(CONSUMER_TYPE);
		data.setStatus(STATUS);
		try {
			String invoiceDataString = new ObjectMapper().writeValueAsString(invoiceHeaderData);
			data.setPayload(invoiceDataString);
		} catch(Exception e) {
			log.error("*****ERROR escaping producer kafka data");
		}
		data.setTraceId(invoiceUtil.getTraceId());
		data.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString());
		data.setIsSecure(false);
		data.setRequestContentType(CONTENT_TYPE);
		kafkaAnalytics.setKafkaAnalyticsData(data);
		return kafkaAnalytics;
	}
	
	/**
	 * Map Producer Kafka Message for EDI API Gateway Analytics
	 * @param completeInvoice
	 * @return
	 */
	public KafkaAnalytics mapProducerKafkaAnalyticsMessage(CompleteInvoice completeInvoice) {
		KafkaAnalytics kafkaAnalytics = new KafkaAnalytics();
		kafkaAnalytics.setSignatureType(APIGATEWAY_ROUTES);
		KafkaAnalyticsData data = new KafkaAnalyticsData();
		data.setSource(applicationName);
		data.setTarget(kafkaTopicInbound);
		data.setType(PRODUCER_TYPE);
		data.setStatus(STATUS);
		try {
			String invoiceDataString = new ObjectMapper().writeValueAsString(completeInvoice);
			data.setPayload(invoiceDataString);
		} catch(Exception e) {
			log.error("*****ERROR escaping producer kafka data");
		}
		data.setTraceId(invoiceUtil.getTraceId());
		data.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toString());
		data.setIsSecure(false);
		data.setRequestContentType(CONTENT_TYPE);
		kafkaAnalytics.setKafkaAnalyticsData(data);
		return kafkaAnalytics;
	}
	
}
