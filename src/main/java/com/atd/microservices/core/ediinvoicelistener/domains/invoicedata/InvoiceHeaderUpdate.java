package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "810_HEADER")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceHeaderUpdate {

	@Field("EDI_HEADER_ID")
	@JsonProperty("EDI_HEADER_ID")
	private Long ediHeaderId;
	
	@Field("ALLOWANCE_COUNT")
	@JsonProperty("ALLOWANCE_COUNT")
	private Long allowanceCount;
	
	@Field("AMOUNT_DUE_ORIGINAL")
	@JsonProperty("AMOUNT_DUE_ORIGINAL")
	private String amountDueOriginal;
	
	@Field("AMOUNT_DUE_REMAINING")
	@JsonProperty("AMOUNT_DUE_REMAINING")
	private String amountDueRemaining;
	
	@Field("ASN_TIME")
	@JsonProperty("ASN_TIME")
	private String asnTime;
	
	@Field("ATTRIBUTE1")
	@JsonProperty("ATTRIBUTE1")
	private String attribute1;
	
	@Field("ATTRIBUTE2")
	@JsonProperty("ATTRIBUTE2")
	private String attribute2;
	
	@Field("ATTRIBUTE3")
	@JsonProperty("ATTRIBUTE3")
	private String attribute3;
	
	@Field("ATTRIBUTE4")
	@JsonProperty("ATTRIBUTE4")
	private String attribute4;
	
	@Field("ATTRIBUTE5")
	@JsonProperty("ATTRIBUTE5")
	private String attribute5;
	
	@Field("BILL_OF_LADING_NUMBER")
	@JsonProperty("BILL_OF_LADING_NUMBER")
	private String billOfLadingNumber;
	
	@Field("BILL_TO_ACCOUNT_NAME")
	@JsonProperty("BILL_TO_ACCOUNT_NAME")
	private String billToAccountName;
	
	@Field("BILL_TO_ACCOUNT_NUMBER")
	@JsonProperty("BILL_TO_ACCOUNT_NUMBER")
	private String billToAccountNumber;
	
	@Field("BILL_TO_ADDRESS1")
	@JsonProperty("BILL_TO_ADDRESS1")
	private String billToAddress1;
	
	@Field("BILL_TO_ADDRESS2")
	@JsonProperty("BILL_TO_ADDRESS2")
	private String billToAddress2;
	
	@Field("BILL_TO_ADDRESS3")
	@JsonProperty("BILL_TO_ADDRESS3")
	private String billToAddress3;
	
	@Field("BILL_TO_ADDRESS4")
	@JsonProperty("BILL_TO_ADDRESS4")
	private String billToAddress4;
	
	@Field("BILL_TO_CITY")
	@JsonProperty("BILL_TO_CITY")
	private String billToCity;
	
	@Field("BILL_TO_COUNTRY")
	@JsonProperty("BILL_TO_COUNTRY")
	private String billToCountry;
	
	@Field("BILL_TO_LOCATION")
	@JsonProperty("BILL_TO_LOCATION")
	private String billToLocation;
	
	@Field("BILL_TO_STATE")
	@JsonProperty("BILL_TO_STATE")
	private String billToState;
	
	@Field("CONSUMER_PO")
	@JsonProperty("CONSUMER_PO")
	private String consumerPo;
	
	@Field("CREDIT_MEMO_REASON")
	@JsonProperty("CREDIT_MEMO_REASON")
	private String creditMemoReason;
	
	@Field("CUST_ACCOUNT_ID")
	@JsonProperty("CUST_ACCOUNT_ID")
	private Long custAccountId;
	
	@Field("CUST_NONSIG_NUMBER")
	@JsonProperty("CUST_NONSIG_NUMBER")
	private String custNonsigNumber;
	
	@Field("DATA_TYPE")
	@JsonProperty("DATA_TYPE")
	private String dataType;
	
	@Field("DELIVERY_NUMBER")
	@JsonProperty("DELIVERY_NUMBER")
	private String deliveryNumber;
	
	@Field("DELV_RECEIPT_NUMBER")
	@JsonProperty("DELV_RECEIPT_NUMBER")
	private String delvReceiptNumber;
	
	@Field("DOCUMENT_TYPE")
	@JsonProperty("DOCUMENT_TYPE")
	private String documentType;
	
	@Field("DOC_TYPE")
	@JsonProperty("DOC_TYPE")
	private String docType;
	
	@Field("DUE_DATE")
	@JsonProperty("DUE_DATE")
	private String dueDate;
	
	@Field("DUE_DAYS")
	@JsonProperty("DUE_DAYS")
	private String dueDays;
	
	@Field("FREIGHT_ORIGINAL")
	@JsonProperty("FREIGHT_ORIGINAL")
	private String freightOriginal;
	
	@Field("FREIGHT_REMAINING")
	@JsonProperty("FREIGHT_REMAINING")
	private String freightRemaining;

	@Field("GROSS_WEIGHT")
	@JsonProperty("GROSS_WEIGHT")
	private String grossWeight;

	@Field("INTERFACE_ATTRIBUTE2")
	@JsonProperty("INTERFACE_ATTRIBUTE2")
	private String interfaceAttribute2;

	@Field("LEGAL_ENTITY_CODE")
	@JsonProperty("LEGAL_ENTITY_CODE")
	private String legalEntityCode;

	@Field("LINES_COUNT")
	@JsonProperty("LINES_COUNT")
	private Long linesCount;

	@Field("ORDER_NUMBER")
	@JsonProperty("ORDER_NUMBER")
	private String orderNumber;

	@Field("ORIG_SYS_DOCUMENT_REF")
	@JsonProperty("ORIG_SYS_DOCUMENT_REF")
	private String origSysDocumentRef;

	@Field("PAYMENT_TERM_NAME")
	@JsonProperty("PAYMENT_TERM_NAME")
	private String paymentTermName;

	@Field("PRIMARY_SALESREP_NAME")
	@JsonProperty("PRIMARY_SALESREP_NAME")
	private String primarySalesrepName;

	@Field("PROCESS_FLAG")
	@JsonProperty("PROCESS_FLAG")
	private String processFlag;

	@Field("PROCESS_MESSAGE")
	@JsonProperty("PROCESS_MESSAGE")
	private String processMessage;

	@Field("PURCHASE_ORDER_NUMBER")
	@JsonProperty("PURCHASE_ORDER_NUMBER")
	private String purchaseOrderNumber;
	
	@Field("RECORD_TYPE")
	@JsonProperty("RECORD_TYPE")
	private String recordType;
	
	@Field("RELATIVE_AMOUNT")
	@JsonProperty("RELATIVE_AMOUNT")
	private String relativeAmount;
	
	@Field("REMIT_TO_ADDRESS1")
	@JsonProperty("REMIT_TO_ADDRESS1")
	private String remitToAddress1;
	
	@Field("REMIT_TO_ADDRESS2")
	@JsonProperty("REMIT_TO_ADDRESS2")
	private String remitToAddress2;
	
	@Field("REMIT_TO_ADDRESS3")
	@JsonProperty("REMIT_TO_ADDRESS3")
	private String remitToAddress3;
	
	@Field("REMIT_TO_ADDRESS4")
	@JsonProperty("REMIT_TO_ADDRESS4")
	private String remitToAddress4;
	
	@Field("REMIT_TO_ADDRESS_LINES_PHONETIC")
	@JsonProperty("REMIT_TO_ADDRESS_LINES_PHONETIC")
	private String remitToAddressLinesPhonetic;
	
	@Field("REMIT_TO_CITY")
	@JsonProperty("REMIT_TO_CITY")
	private String remitToCity;
	
	@Field("REMIT_TO_COUNTRY")
	@JsonProperty("REMIT_TO_COUNTRY")
	private String remitToCountry;
	
	@Field("REMIT_TO_ORIG_SYSTEM_REFERENCE")
	@JsonProperty("REMIT_TO_ORIG_SYSTEM_REFERENCE")
	private String remitToOrigSystemReference;
	
	@Field("REMIT_TO_POSTAL_CODE")
	@JsonProperty("REMIT_TO_POSTAL_CODE")
	private String remitToPostalCode;
	
	@Field("REMIT_TO_STATE")
	@JsonProperty("REMIT_TO_STATE")
	private String remitToState;
	
	@Field("SHIPMENT_DATE")
	@JsonProperty("SHIPMENT_DATE")
	private String shipmentDate;
	
	@Field("SHIP_TO_ACCOUNT_NAME")
	@JsonProperty("SHIP_TO_ACCOUNT_NAME")
	private String shipToAccountName;
	
	@Field("SHIP_TO_ACCOUNT_NUMBER")
	@JsonProperty("SHIP_TO_ACCOUNT_NUMBER")
	private String shipToAccountNumber;
	
	@Field("SHIP_TO_ADDRESS1")
	@JsonProperty("SHIP_TO_ADDRESS1")
	private String shipToAddress1;
	
	@Field("SHIP_TO_ADDRESS2")
	@JsonProperty("SHIP_TO_ADDRESS2")
	private String shipToAddress2;
	
	@Field("SHIP_TO_ADDRESS3")
	@JsonProperty("SHIP_TO_ADDRESS3")
	private String shipToAddress3;
	
	@Field("SHIP_TO_ADDRESS4")
	@JsonProperty("SHIP_TO_ADDRESS4")
	private String shipToAddress4;
	
	@Field("SHIP_TO_CITY")
	@JsonProperty("SHIP_TO_CITY")
	private String shipToCity;
	
	@Field("SHIP_TO_COUNTRY")
	@JsonProperty("SHIP_TO_COUNTRY")
	private String shipToCountry;
	
	@Field("SHIP_TO_LOCATION")
	@JsonProperty("SHIP_TO_LOCATION")
	private String shipToLocation;
	
	@Field("SHIP_TO_POSTAL_CODE")
	@JsonProperty("SHIP_TO_POSTAL_CODE")
	private String shipToPostalCode;
	
	@Field("SHIP_TO_STATE")
	@JsonProperty("SHIP_TO_STATE")
	private String shipToState;
	
	@Field("SHIP_VIA")
	@JsonProperty("SHIP_VIA")
	private String shipVia;
	
	@Field("STORE")
	@JsonProperty("STORE")
	private String store;
	
	@Field("TAX_COUNT")
	@JsonProperty("TAX_COUNT")
	private Long taxCount;
	
	@Field("TAX_ORIGINAL")
	@JsonProperty("TAX_ORIGINAL")
	private String taxOriginal;
	
	@Field("TAX_REMAINING")
	@JsonProperty("TAX_REMAINING")
	private String taxRemaining;
	
	@Field("TOTAL_ITEM_UNIT_FET")
	@JsonProperty("TOTAL_ITEM_UNIT_FET")
	private String totalItemUnitFet;
	
	@Field("TRANSACTION_ATTRIBUTE3")
	@JsonProperty("TRANSACTION_ATTRIBUTE3")
	private String transactionAttribute3;
	
	@Field("TRANSACTION_ATTRIBUTE7")
	@JsonProperty("TRANSACTION_ATTRIBUTE7")
	private String transactionAttribute7;
	
	@Field("TRANSLATOR_CODE")
	@JsonProperty("TRANSLATOR_CODE")
	private String translatorCode;
	
	@Field("TRX_CREATION_DATE")
	@JsonProperty("TRX_CREATION_DATE")
	private String trxCreationDate;
	
	@Field("TRX_NUMBER")
	@JsonProperty("TRX_NUMBER")
	private String trxNumber;

}
