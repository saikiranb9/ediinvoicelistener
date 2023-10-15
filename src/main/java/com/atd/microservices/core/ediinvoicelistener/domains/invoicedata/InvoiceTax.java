package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection = "810_TAX")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceTax {
	@Field("EDI_TAX_ID")
	private Long ediTaxId;

	@Field("EDI_LINE_ID")
	private Long ediLineId;

	@Field("EDI_HEADER_ID")
	private String ediHeaderId;

	@Field("ATTRIBUTE1")
	private String attribute1;
	
	@Field("ATTRIBUTE2")
	private String attribute2;
	
	@Field("ATTRIBUTE3")
	private String attribute3;
	
	@Field("ATTRIBUTE4")
	private String attribute4;
	
	@Field("ATTRIBUTE5")
	private String attribute5;

	@Field("CUST_ACCOUNT_ID")
	private String custAccountId;

	@Field("DOC_TYPE")
	private String docType;

	@Field("LINE_NUMBER")
	private String lineNumber;

	@Field("PROCESS_FLAG")
	private String processFlag;

	@Field("PROCESS_MESSAGE")
	private String processMessage;

	@Field("RECORD_TYPE")
	private String recordType;

	@Field("REM_TAX_AMOUNT")
	private Long remTaxAmount;

	@Field("TAX_AMOUNT")
	private String taxAmount;

	@Field("TAX_RATE")
	private String taxRate;

	@Field("TP_TRANSLATOR_CODE")
	private String tpTranslatorCode;

	@Field("TRX_NUMBER")
	private String trxNumber;

	@Field("VAT_TAX_CODE_INT")
	private String vatTaxCodeInt;

}
