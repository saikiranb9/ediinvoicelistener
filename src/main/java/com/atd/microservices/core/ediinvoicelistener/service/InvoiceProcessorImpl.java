package com.atd.microservices.core.ediinvoicelistener.service;

import static com.atd.microservices.core.ediinvoicelistener.config.AppConstants.*;

import com.atd.microservices.core.ediinvoicelistener.config.KafkaConfigConstants;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.BacklogRecord;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.CompleteInvoice;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiData;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceCommonData;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceData;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceDataDTO;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceHeaderUpdate;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceLine;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.KafkaAnalytics;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.ResponseInvoiceHeader;
import com.atd.microservices.core.ediinvoicelistener.repository.InvoiceAllowanceRepository;
import com.atd.microservices.core.ediinvoicelistener.repository.InvoiceLineRepository;
import com.atd.microservices.core.ediinvoicelistener.repository.InvoiceTaxRepository;
import com.atd.microservices.core.ediinvoicelistener.webclient.clients.EdiAnalyticsDataClient;
import com.atd.microservices.core.ediinvoicelistener.webclient.clients.EdiConfigClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBObject;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableAsync
public class InvoiceProcessorImpl implements InvoiceProcessor {

	@Value("${kafka.analytic.topic}")
	private String kafkaTopicAnalytics;

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

	@Value("${process.flag.field}")
	private String processFlagField;

	@Value("${line.number.field}")
	private String lineNumberField;

	@Value("${document.type.field}")
	private String documentTypeField;

	@Value("${line.collection}")
	private String lineCollection;

	@Value("${tax.collection}")
	private String taxCollection;

	@Value("${allowance.collection}")
	private String allowanceCollection;

	@Autowired
	BeanFactory beanFactory;

	@Autowired
	InvoiceAllowanceRepository invoiceAllowanceRepository;

	@Autowired
	InvoiceTaxRepository invoiceTaxRepository;

	@Autowired
	InvoiceLineRepository invoiceLineRepository;

	@Autowired
	EdiConfigClient ediConfigClient;

	@Autowired
	EdiAnalyticsDataClient ediAnalyticsDataClient;

	@Autowired
	KafkaConfigConstants kafkaConfigConstants;

	@Autowired
	KafkaSender kafkaSender;

	@Autowired
	InvoiceUtil invoiceUtil;

	@Autowired
	MongoTemplate mongoTemplate;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Async
	public void processInvoice(JsonNode invoiceHeaderData, JsonNode invoiceHeaderPayload) throws Exception {
		/*
		 * Create DTO
		 */
		InvoiceDataDTO invoiceDataDto = new InvoiceDataDTO();
		invoiceDataDto.setInvoiceHeader(invoiceHeaderPayload);
		if (null != invoiceHeaderPayload.get(trxNumberField)
				&& null != invoiceHeaderPayload.get(trxNumberField).asText()
				&& !invoiceHeaderPayload.get(trxNumberField).asText().isEmpty()) {
			invoiceDataDto.setTrxNumber(invoiceHeaderPayload.get(trxNumberField).asText());
		} else {
			throw new Exception("NO ASN NUMBER FOUND IN PAYLOAD");
		}
		if (null != invoiceHeaderPayload.get(customerIdField)
				&& null != invoiceHeaderPayload.get(customerIdField).asText()
				&& !invoiceHeaderPayload.get(customerIdField).asText().isEmpty()) {
			invoiceDataDto.setCustAccountId(invoiceHeaderPayload.get(customerIdField).asLong());
		} else {
			throw new Exception("NO CUSTOMER ACCOUNT ID FOUND IN PAYLOAD");
		}
		if (null != invoiceHeaderPayload.get(lineCountField)
				&& null != invoiceHeaderPayload.get(lineCountField).asText()
				&& !invoiceHeaderPayload.get(lineCountField).asText().isEmpty()
				&& invoiceHeaderPayload.get(lineCountField).asLong() != 0) {
			invoiceDataDto.setLineCount(invoiceHeaderPayload.get(lineCountField).asLong());
		} else {
			throw new Exception("NO LINE COUNT FOUND IN PAYLOAD");
		}
		if (null != invoiceHeaderPayload.get(taxCountField) && null != invoiceHeaderPayload.get(taxCountField).asText()
				&& !invoiceHeaderPayload.get(taxCountField).asText().isEmpty()
				&& invoiceHeaderPayload.get(taxCountField).asLong() != 0) {
			invoiceDataDto.setTaxCount(invoiceHeaderPayload.get(taxCountField).asLong());
		} else {
			throw new Exception("NO TAX COUNT FOUND IN PAYLOAD");
		}
		if (null != invoiceHeaderPayload.get(allowanceCountField)
				&& null != invoiceHeaderPayload.get(allowanceCountField).asText()
				&& !invoiceHeaderPayload.get(allowanceCountField).asText().isEmpty()
				&& invoiceHeaderPayload.get(allowanceCountField).asLong() != 0) {
			invoiceDataDto.setAllowanceCount(invoiceHeaderPayload.get(allowanceCountField).asLong());
		} else {
			throw new Exception("NO ALLOWANCE COUNT FOUND IN PAYLOAD");
		}

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
		 * Query MongoDB for lines, taxes, and allowances in parallel
		 */
		try {
			futures.add(CompletableFuture
					.supplyAsync(() -> getLinesByTrx(invoiceDataDto.getTrxNumber(), invoiceDataDto), executor));
			futures.add(CompletableFuture
					.supplyAsync(() -> getTaxesByTrx(invoiceDataDto.getTrxNumber(), invoiceDataDto), executor));
			futures.add(CompletableFuture
					.supplyAsync(() -> getAllowancesByTrx(invoiceDataDto.getTrxNumber(), invoiceDataDto), executor));
			futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception("Failed to get lines, taxes, and allowances from MongoDB : " + e.getMessage());
		} finally {
			executor.shutdown();
		}
		/*
		 * Check number of lines, number of taxes, and number of allowances and compare
		 * to the value from the Invoice header
		 */
		Integer headerLinesCount = invoiceDataDto.getLineCount().intValue();
		Integer headerTaxesCount = invoiceDataDto.getTaxCount().intValue();
		Integer headerAllowancesCount = invoiceDataDto.getAllowanceCount().intValue();

		Integer actualLinesCount = invoiceDataDto.getInvoiceLines().size();
		Integer actualTaxesCount = invoiceDataDto.getInvoiceTaxes().size();
		Integer actualAllowancesCount = invoiceDataDto.getInvoiceAllowances().size();

		if (actualLinesCount.equals(headerLinesCount) && actualTaxesCount.equals(headerTaxesCount)
				&& actualAllowancesCount.equals(headerAllowancesCount) && null != invoiceDataDto.getInvoiceLines()
				&& !invoiceDataDto.getInvoiceLines().isEmpty()) {
			/*
			 * Get EDI Config data to append to response message
			 */
			if (null != invoiceDataDto.getCustAccountId()) {
				getEdiConfigData(invoiceDataDto);
			} else {
				throw new Exception("TRX Number : " + invoiceDataDto.getTrxNumber()
						+ " -- Invoice Header does not contain Customer Account ID");
			}
			/*
			 * Map Invoice and submit to Kafka topic
			 */
			if (null != invoiceDataDto.getEdiConfigResponse()) {
				try {
					// ----------------------------------------------------------------------------------------
					EdiData ediData = invoiceUtil.mapConsumerEdiData(invoiceHeaderData, invoiceHeaderPayload,
							invoiceDataDto.getEdiConfigResponse(), invoiceDataDto.getTrxNumber());
					ediAnalyticsDataClient.saveEDIData(ediData).block();
					// ----------------------------------------------------------------------------------------
					/*
					 * Map Invoice and submit to Kafka topic
					 */
					createCompleteInvoiceAndSubmitToKafka(invoiceDataDto);
					/*
					 * If successfully pushed to Kafka, update PROCESS_FLAG in MongoDB to 'Y'
					 */
					if (null != invoiceHeaderPayload.get(processFlagField)
							&& null != invoiceHeaderPayload.get(processFlagField).asText()
							&& invoiceHeaderPayload.get(processFlagField).asText().isEmpty()) {
						Update update = new Update();
						update.set(PROCESS_FLAG, "Y");
						Query query = new Query();
						query.addCriteria(Criteria.where(TRX_NUMBER).is(invoiceDataDto.getTrxNumber()));
						mongoTemplate.findAndModify(query, update, InvoiceHeaderUpdate.class);
						log.info("Updated PROCESS_FLAG to 'Y' for TRX : {}", invoiceDataDto.getTrxNumber());
					}
				} catch (Exception e) {
					/*
					 * Submit error data to edi analytics data for Transactions page
					 */
					// ----------------------------------------------------------------------------------------
					EdiData ediData = invoiceUtil.mapErrorEdiData("Error Mapping Order Hierarchy : " + e.getMessage(),
							invoiceDataDto.getEdiConfigResponse(), invoiceDataDto.getTrxNumber());
					ediAnalyticsDataClient.saveEDIData(ediData).block();
					// ----------------------------------------------------------------------------------------
				}
			} else {
				log.error("EDI Config Data Not found for Invoice : {}, Customer Account ID : {}",
						invoiceDataDto.getTrxNumber(), invoiceDataDto.getCustAccountId());
			}
		} else {
			log.info(
					"Line, Tax, or Allowance count does not match -> "
							+ "\nEXPECTED LINE COUNT : {}, ACTUAL LINE COUNT {} "
							+ "\nEXPECTED TAX COUNT : {}, ACTUAL TAX COUNT {}"
							+ "\nEXPECTED ALLOWANCE COUNT : {}, ACTUAL ALLOWANCE COUNT {}",
					headerLinesCount, actualLinesCount, headerTaxesCount, actualTaxesCount, headerAllowancesCount,
					actualAllowancesCount);
			/*
			 * If the number of lines, number of taxes, or number of allowances counts don't
			 * match the expected values, save the Invoice Header to MongoDB
			 */
			BacklogRecord mongoRecord = new BacklogRecord();
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(invoiceHeaderPayload);
			mongoRecord.setTrxNumber(invoiceHeaderPayload.get(trxNumberField).asText());
			mongoRecord.setPayload(json);
			mongoTemplate.save(mongoRecord);
			log.info("Inserted to Backlog MongoDB -- TRX Number : {}", invoiceDataDto.getTrxNumber());
		}
	}

	@Override
	public InvoiceCommonData getLinesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto) {
		Query query = new Query();
		query.addCriteria(Criteria.where(trxNumberField).is(trxNumber));
		List<DBObject> messageList = mongoTemplate.find(query, DBObject.class, lineCollection);
		/*
		 * Reformat Date Fields from MongoDB
		 */
		ObjectMapper mapper = new ObjectMapper();
		List<JsonNode> lines = replaceObjectTypesInDBObject(messageList, mapper);

		invoiceDataDto.setInvoiceLines(mapper.valueToTree(lines));
		return invoiceDataDto;
	}

	@Override
	public InvoiceCommonData getTaxesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto) {
		Query query = new Query();
		query.addCriteria(Criteria.where(trxNumberField).is(trxNumber));
		List<DBObject> messageList = mongoTemplate.find(query, DBObject.class, taxCollection);
		/*
		 * Reformat Date Fields from MongoDB
		 */
		ObjectMapper mapper = new ObjectMapper();
		List<JsonNode> taxes = replaceObjectTypesInDBObject(messageList, mapper);

		invoiceDataDto.setInvoiceTaxes(mapper.valueToTree(taxes));
		return invoiceDataDto;
	}

	@Override
	public InvoiceCommonData getAllowancesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto) {
		Query query = new Query();
		query.addCriteria(Criteria.where(trxNumberField).is(trxNumber));
		List<DBObject> messageList = mongoTemplate.find(query, DBObject.class, allowanceCollection);
		/*
		 * Reformat Date Fields from MongoDB
		 */
		ObjectMapper mapper = new ObjectMapper();
		List<JsonNode> allowances = replaceObjectTypesInDBObject(messageList, mapper);

		invoiceDataDto.setInvoiceAllowances(mapper.valueToTree(allowances));
		return invoiceDataDto;
	}

	@Override
	public InvoiceCommonData getEdiConfigData(InvoiceDataDTO invoiceDataDto) {
		ediConfigClient.getEdiConfigData(invoiceDataDto).map(value -> {
			invoiceDataDto.setEdiConfigResponse(value);
			return invoiceDataDto;
		}).collectList().block();
		return invoiceDataDto;
	}

	@Override
	public void createCompleteInvoiceAndSubmitToKafka(InvoiceDataDTO invoiceDataDto) {
		CompleteInvoice kafkaMessage = mapCompleteInvoice(invoiceDataDto);
		kafkaSender.submitInvoiceToKafka(kafkaConfigConstants.KAFKA_TOPIC_INBOUND, kafkaMessage);
		// ----------------------------------------------------------------------------------------
		/*
		 * Send Producer Analytics Kafka Message
		 */
		KafkaAnalytics kafkaAnalytics = kafkaSender.mapProducerKafkaAnalyticsMessage(kafkaMessage);
		kafkaSender.submitAnalyticsToKafka(kafkaTopicAnalytics, kafkaAnalytics);
		/*
		 * Send Producer Transaction Analytics
		 */
		EdiData ediData = invoiceUtil.mapProducerEdiData(kafkaMessage, invoiceDataDto.getEdiConfigResponse(),
				invoiceDataDto.getTrxNumber());
		ediAnalyticsDataClient.saveEDIData(ediData).block();
		// ----------------------------------------------------------------------------------------
	}

	@Override
	public CompleteInvoice mapCompleteInvoice(InvoiceDataDTO invoiceDataDto) {
		ObjectMapper objectMapper = new ObjectMapper();
		/*
		 * Line ArrayNode to List of JsonNode Conversion Logic
		 */
		List<JsonNode> lineList = new ArrayList<>();
		try {
			lineList = objectMapper.readValue(objectMapper.writeValueAsString(invoiceDataDto.getInvoiceLines()),
					new TypeReference<List<JsonNode>>() {
					});
		} catch (JsonProcessingException e) {
			log.error("Error processing Line, Tax, and Allowance MongoDB data to JSON");
		}
		/*
		 * Sort by line number
		 */
		Collections.sort(lineList, new Comparator<JsonNode>() {
			@Override
			public int compare(JsonNode line1, JsonNode line2) {
				return Long.valueOf(line1.get(lineNumberField).asLong())
						.compareTo(Long.valueOf(line2.get(lineNumberField).asLong()));
			}
		});
		/*
		 * Set Taxes and Allowances for each line
		 */
		lineList.stream().forEach(line -> {
			try {
				List<JsonNode> taxList = objectMapper.readValue(
						objectMapper.writeValueAsString(invoiceDataDto.getInvoiceTaxes()),
						new TypeReference<List<JsonNode>>() {
						});
				((ObjectNode) line).set(TAXES,
						objectMapper.valueToTree(taxList.stream()
								.filter(tax -> tax.get(lineNumberField).equals(line.get(lineNumberField)))
								.collect(Collectors.toList())));
				List<JsonNode> allowanceList = objectMapper.readValue(
						objectMapper.writeValueAsString(invoiceDataDto.getInvoiceAllowances()),
						new TypeReference<List<JsonNode>>() {
						});
				((ObjectNode) line).set(ALLOWANCES,
						objectMapper.valueToTree(allowanceList.stream()
								.filter(allowance -> allowance.get(lineNumberField).equals(line.get(lineNumberField)))
								.collect(Collectors.toList())));
			} catch (JsonProcessingException e) {
				log.error("Error processing Taxes and Allowances : failed to set to lines");
			}
		});

		/*
		 * Add lines to invoice header
		 */
		((ObjectNode) invoiceDataDto.getInvoiceHeader()).set(LINES, objectMapper.valueToTree(lineList));
		/*
		 * Form a complete Invoice
		 */
		CompleteInvoice completeInvoice = new CompleteInvoice();
		/*
		 * Map header level EDI data
		 */
		completeInvoice.setType(TYPE);
		completeInvoice.setStandard(invoiceDataDto.getEdiConfigResponse().getStandard810());
		completeInvoice.setVersion(invoiceDataDto.getEdiConfigResponse().getVersion810());
		completeInvoice.setSendercode(invoiceDataDto.getEdiConfigResponse().getReceiverCode());
		if (null != invoiceDataDto.getInvoiceHeader().get(documentTypeField)
				&& !invoiceDataDto.getInvoiceHeader().get(documentTypeField).asText().isEmpty()
				&& !invoiceDataDto.getEdiConfigResponse().getSenderCode()
						.endsWith("-".concat(invoiceDataDto.getInvoiceHeader().get(documentTypeField).asText()))) {
			invoiceDataDto.getEdiConfigResponse().setSenderCode(invoiceDataDto.getEdiConfigResponse().getSenderCode()
					.concat("-" + invoiceDataDto.getInvoiceHeader().get(documentTypeField).asText()));
		}
		completeInvoice.setReceivercode(invoiceDataDto.getEdiConfigResponse().getSenderCode());

		InvoiceData invoiceData = new InvoiceData();
		invoiceData.setEdiConfigResponse(invoiceDataDto.getEdiConfigResponse());
		invoiceData.getEdiConfigResponse().setReceiverCode(invoiceDataDto.getEdiConfigResponse().getReceiverCode());
		invoiceData.setHeader(invoiceDataDto.getInvoiceHeader());

		try {
			String invoiceDataString = new ObjectMapper().writeValueAsString(invoiceData);
			completeInvoice.setData(invoiceDataString);
		} catch (JsonProcessingException e) {
			log.error("*****ERROR escaping payload data");
		}

		return completeInvoice;
	}

	/**
	 * DBObjects return Date fields from MongoDB as nested objects This code will
	 * collect all of the nested date fields and replace their values with a Date
	 * Time converted string
	 */
	@Override
	public List<JsonNode> replaceObjectTypesInDBObject(List<DBObject> messageList, ObjectMapper mapper) {
		List<JsonNode> records = new ArrayList<>();
		messageList.forEach(message -> {
			try {
				Map<String, String> dateOverwriteMap = new HashMap<>();
				JsonNode record = mapper.readTree(message.toString());
				Iterator<Entry<String, JsonNode>> fields = record.fields();
				while (fields.hasNext()) {
					Entry<String, JsonNode> field = fields.next();
					// log.info("JsonNodeType : {}", field.getValue().getNodeType());
					if (field.getValue().getNodeType().equals(JsonNodeType.OBJECT)) {
						String dateFieldValue = null;
						Iterator<Entry<String, JsonNode>> objectFields = field.getValue().fields();
						Entry<String, JsonNode> objectField = objectFields.next();
						if (objectField.getKey().equalsIgnoreCase("$date")) {
							dateFieldValue = objectField.getValue().asText();
							LocalDateTime dateTime = Instant.ofEpochMilli(Long.valueOf(dateFieldValue))
									.atZone(ZoneId.systemDefault()).toLocalDateTime();
							dateOverwriteMap.put(field.getKey(), dateTime.toString());
							fields.remove();
						}
					}
				}
				((ObjectNode) record).remove(ID);
				dateOverwriteMap.entrySet().forEach(entry -> {
					((ObjectNode) record).put(entry.getKey(), entry.getValue());
				});
				records.add(record);
			} catch (JsonProcessingException e) {
				log.error("Error Parsing DB Object to Json");
			}
		});
		return records;
	}
}
