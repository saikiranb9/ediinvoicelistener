package com.atd.microservices.core.ediinvoicelistener.service.listener;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.KafkaAnalytics;
import com.atd.microservices.core.ediinvoicelistener.service.InvoiceProcessor;
import com.atd.microservices.core.ediinvoicelistener.service.KafkaSender;
import com.atd.microservices.core.ediinvoicelistener.service.ReaderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvoiceHeaderListener {
	
	@Value("${kafka.analytic.topic}")
	private String kafkaTopicAnalytics;
	
	@Value("${trx.number.field}")
	private String trxNumberField;
	
	@Autowired
	KafkaSender kafkaSender;
	
	@Autowired
	ReaderUtil readerUtil;
	
	@Autowired
    InvoiceProcessor invoiceProcessor;

    @KafkaListener(topics = "${kafka.topic.outbound}", groupId = "${kafka.groupid}", containerFactory = "kafkaListenerContainerFactory")
    public void analyticsMessageListener(JsonNode invoiceHeaderData, Consumer<?,?> consumer) throws Exception {
    	/*
    	 * Log Incoming ERP Message
    	 */
    	try {	
	    	ObjectMapper mapper = new ObjectMapper();
			String invoiceString = mapper.writeValueAsString(invoiceHeaderData);
			log.info("*****Message Received From ERP : {}", invoiceString);
	    } catch (JsonProcessingException e) {
			log.error("Error parsing Golden Gate message: " + e.getMessage());
		}
    	/*
    	 * Extract the ERP message from the Json Node based on the root and node key configs
    	 */
    	JsonNode payload = readerUtil.getDataFromMessage(invoiceHeaderData);
    	//----------------------------------------------------------------------------------------
    	/*
    	 * Send Consumer Analytics Kafka Message
    	 */
    	KafkaAnalytics kafkaAnalytics = kafkaSender.mapConsumerKafkaAnalyticsMessage(invoiceHeaderData);
    	kafkaSender.submitAnalyticsToKafka(kafkaTopicAnalytics, kafkaAnalytics);
		//----------------------------------------------------------------------------------------
    	if(null != payload.get(trxNumberField)
				&& null != payload.get(trxNumberField).asText()
				&& !payload.get(trxNumberField).asText().isEmpty()) {
    		log.info("*****Consumer Analytics Message Sent for Invoice : {}", payload.get(trxNumberField).asText());
		} else {
			log.error("NO TRX NUMBER FOUND IN PAYLOAD");
			throw new Exception();
		}
	    /*
	     * Process Golden Gate Message
	     */
    	try {
    		invoiceProcessor.processInvoice(invoiceHeaderData, payload);
        } catch (Exception e) {
            log.error("Failed to process Kafka message : {}", e);
        }
    }
}
