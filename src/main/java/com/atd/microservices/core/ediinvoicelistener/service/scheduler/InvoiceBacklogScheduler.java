package com.atd.microservices.core.ediinvoicelistener.service.scheduler;

import static com.atd.microservices.core.ediinvoicelistener.config.AppConstants.*;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.BacklogRecord;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiConfigResponse;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceCommonData;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceDataDTO;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceHeader;
import com.atd.microservices.core.ediinvoicelistener.repository.InvoiceBacklogRepository;
import com.atd.microservices.core.ediinvoicelistener.service.InvoiceProcessor;
import com.atd.microservices.core.ediinvoicelistener.service.KafkaSender;
import com.atd.microservices.core.ediinvoicelistener.webclient.clients.EdiConfigClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InvoiceBacklogScheduler {
	
	@Value("${trx.number.field}")
	private String trxNumberField;
	
	@Value("${customer.id.field}")
	private String customerIdField;
	
	@Value("${line.count.field}")
	private String lineCountField;
	
	@Value("${tax.count.field}")
	private String taxCountField;
	
	@Value("${allowance.count.field}")
	private String allowanceCountField;
	
	@Autowired
	BeanFactory beanFactory;
	
	@Autowired
	InvoiceProcessor invoiceProcessor;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
    @Autowired
    InvoiceBacklogRepository invoiceBacklogRepository;
    
    @Autowired
    EdiConfigClient ediConfigClient;
    
    @Autowired
    KafkaSender kafkaSender;

    @Scheduled(cron = "${backlog.cron.expression}")
    public void ScheduledBacklogCheck() {
    	/*
    	 * Collect all EDI Configs
    	 */
    	log.info("Pulling EDI Configs for backlog items...");
    	Map<String, EdiConfigResponse> ediConfigs = ediConfigClient.getAllConfigs()
    			.collectMap(config -> config.getCustomerID(), config -> config).block();
    	/*
    	 * Get all Backlog Items
    	 */
    	List<BacklogRecord> backlogList = invoiceBacklogRepository.findAll().collectList().block();
    	if(null != backlogList && !backlogList.isEmpty()) {
    		backlogList.stream().forEach(backlogHeaderString -> {
    			if(null != backlogHeaderString.getPayload()) {
	    			InvoiceDataDTO invoiceDataDto = new InvoiceDataDTO();
	    			/*
					 * Convert back to Json Node from string
					 */
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode backlogHeader = null;
					try {
						backlogHeader = objectMapper.readTree(backlogHeaderString.getPayload());
					} catch (JsonProcessingException e1) {
						log.error("Error processing from string to JSON : {}" , e1.getMessage());
					}
					/*
					 * Set TRX Number
					 */
					String trxNumber = backlogHeader.get(trxNumberField).asText();
					/*
					 * Set Customer Account ID
					 */
					String custAccountId = backlogHeader.get(customerIdField).asText();
					
					log.info("*****Processing backlog Invoice : {}", trxNumber);
					/*
					 * Set DTO Header
					 */
					invoiceDataDto.setInvoiceHeader(backlogHeader);
					/*
					 * Set EDI Config for record
					 */
					invoiceDataDto.setEdiConfigResponse(ediConfigs.get(custAccountId));
	    			/*
	    	         * Create Traceable Executor
	    	         */
	    	    	TraceableExecutorService executor = new TraceableExecutorService(beanFactory,
	    					Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()), "SpanId");
	    	    	/*
	    	    	 * Create Futures List
	    	    	 */
	    	    	List<CompletableFuture<InvoiceCommonData>> futures = new ArrayList<>();
	    	    	/*
	    	    	 * Query MongoDB for Orders and Lines in parallel
	    	    	 */
	    	    	try {
	    		    	futures.add(CompletableFuture.supplyAsync(() -> invoiceProcessor.getLinesByTrx(trxNumber, invoiceDataDto)));
	    		    	futures.add(CompletableFuture.supplyAsync(() -> invoiceProcessor.getTaxesByTrx(trxNumber, invoiceDataDto)));
	    		    	futures.add(CompletableFuture.supplyAsync(() -> invoiceProcessor.getAllowancesByTrx(trxNumber, invoiceDataDto)));
	    		    	futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	    	    	} catch(Exception e) {
	    	    		log.error("Error getting lines, taxes, and allowances for backlog items : {}", e.getMessage());
	    	    	} finally {
	    	    		executor.shutdown();
	    	    	}
	    	    	/*
	    	    	 * Compare number of orders and lines to 
	    	    	 */
	    	    	Integer headerLinesCount = backlogHeader.get(lineCountField).intValue();
	    	    	Integer headerTaxCount = backlogHeader.get(taxCountField).intValue();
	    	    	Integer headerAllowanceCount = backlogHeader.get(allowanceCountField).intValue();
	    	    	
	    	    	Integer actualLinesCount = invoiceDataDto.getInvoiceLines().size();
	    	    	Integer actualTaxCount = invoiceDataDto.getInvoiceTaxes().size();
	    	    	Integer actualAllowanceCount = invoiceDataDto.getInvoiceAllowances().size();
	    	    	
	    	    	if(actualLinesCount.equals(headerLinesCount) 
	    	    			&& actualTaxCount.equals(headerTaxCount)
	    	    			&& actualAllowanceCount.equals(headerAllowanceCount)) {
	    	    		
	    	    		if(null != invoiceDataDto.getEdiConfigResponse()) {
		    	    		/*
		    	    		 * Map Invoice and submit to Kafka topic
		    	    		 */
	    	    			invoiceProcessor.createCompleteInvoiceAndSubmitToKafka(invoiceDataDto);
		    	    		/*
		    	    		 * Remove the record from MongoDB
		    	    		 */
		    	    		Query query = Query.query(Criteria.where("trxNumber").is(backlogHeaderString.getTrxNumber()));
		    	    		mongoTemplate.findAndRemove(query, BacklogRecord.class);
	    	    		} else {
	    	    			log.error("Backlog Invoice : EDI Config Data Not found for TRX Number : {}, Customer Account ID : {}", 
	    	    					trxNumber, custAccountId);
	    	    		}
	    	    		
	    	    	}
    			}
    		});
    	} else return;
    }
}