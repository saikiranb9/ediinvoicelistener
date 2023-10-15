package com.atd.microservices.core.ediinvoicelistener.service;

import java.util.List;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.CompleteInvoice;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceCommonData;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceDataDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;

public interface InvoiceProcessor {

	public void processInvoice(JsonNode invoiceHeaderData, JsonNode payload) throws Exception;
	
	public InvoiceCommonData getLinesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto);
	
	public InvoiceCommonData getTaxesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto);
	
	public InvoiceCommonData getAllowancesByTrx(String trxNumber, InvoiceDataDTO invoiceDataDto);
	
	public InvoiceCommonData getEdiConfigData(InvoiceDataDTO invoiceDataDto);
	
	public void createCompleteInvoiceAndSubmitToKafka(InvoiceDataDTO invoiceDataDto);
	
	public CompleteInvoice mapCompleteInvoice(InvoiceDataDTO invoiceDataDto);
	
	public List<JsonNode> replaceObjectTypesInDBObject(List<DBObject> messageList, ObjectMapper mapper);
}

