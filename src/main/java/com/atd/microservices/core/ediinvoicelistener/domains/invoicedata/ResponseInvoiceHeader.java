package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"ediHeaderId",
	"allowanceCount",
	"amountDueOriginal",
	"amountDueRemaining",
	"attribute1",
	"attribute2",
	"attribute3",
	"attribute4",
	"attribute5",
	"billOfLadingNumber",
	"billToAccountName",
	"billToAccountNumber",
	"billToAddress1",
	"billToAddress2",
	"billToAddress3",
	"billToAddress4",
	"billToCity",
	"billToCountry",
	"billToLocation",
	"billToState",
	"consumerPo",
	"creditMemoReason",
	"custAccountId",
	"custNonsigNumber",
	"dataType",
	"deliveryNumber",
	"delvReceiptNumber",
	"documentType",
	"docType",
	"dueDate",
	"dueDays",
	"freightOriginal",
	"freightRemaining",
	"grossWeight",
	"interfaceAttribute2",
	"legalEntityCode",
	"origSysDocumentRef",
	"paymentTermName",
	"primarySalesrepName",
	"processFlag",
	"processMessage",
	"purchaseOrderNumber",
	"recordType",
	"relativeAmount",
	"remitToAddress1",
	"remitToAddress2",
	"remitToAddress3",
	"remitToAddress4",
	"remitToAddressLinesPhonetic",
	"remitToCity",
	"remitToCountry",
	"remitToOrigSystemReference",
	"remitToPostalCode",
	"remitToState",
	"shipmentDate",
	"shipToAccountName",
	"shipToAccountNumber",
	"shipToAddress1",
	"shipToAddress2",
	"shipToAddress3",
	"shipToAddress4",
	"shipToCity",
	"shipToCountry",
	"shipToLocation",
	"shipToPostalCode",
	"shipToState",
	"shipVia",
	"store",
	"taxCount",
	"taxOriginal",
	"taxRemaining",
	"totalItemUnitFet",
	"transactionAttribute3",
	"transactionAttribute7",
	"translatorCode",
	"trxCreationDate",
	"trxNumber",
	"orderNumber",
	"linesCount",
	"invoiceLines",
	"invoiceTaxes",
	"invoiceAllowances"
})
public class ResponseInvoiceHeader {

	private Long ediHeaderId;

	private Long allowanceCount;

	private String amountDueOriginal;

	private String amountDueRemaining;

	private String attribute1;

	private String attribute2;

	private String attribute3;

	private String attribute4;

	private String attribute5;

	private String billOfLadingNumber;

	private String billToAccountName;

	private String billToAccountNumber;

	private String billToAddress1;

	private String billToAddress2;

	private String billToAddress3;

	private String billToAddress4;

	private String billToCity;

	private String billToCountry;

	private String billToLocation;

	private String billToState;

	private String consumerPo;

	private String creditMemoReason;

	private Long custAccountId;

	private String custNonsigNumber;

	private String dataType;

	private String deliveryNumber;

	private String delvReceiptNumber;

	private String documentType;

	private String docType;

	private String dueDate;

	private String dueDays;

	private String freightOriginal;

	private String freightRemaining;

	private String grossWeight;

	private String interfaceAttribute2;

	private String legalEntityCode;

	private String origSysDocumentRef;

	private String paymentTermName;

	private String primarySalesrepName;

	private String processFlag;

	private String processMessage;

	private String purchaseOrderNumber;

	private String recordType;

	private String relativeAmount;

	private String remitToAddress1;

	private String remitToAddress2;

	private String remitToAddress3;

	private String remitToAddress4;

	private String remitToAddressLinesPhonetic;

	private String remitToCity;

	private String remitToCountry;
	
	private String remitToOrigSystemReference;

	private String remitToPostalCode;

	private String remitToState;

	private String shipmentDate;

	private String shipToAccountName;

	private String shipToAccountNumber;
	
	private String shipToAddress1;
	
	private String shipToAddress2;

	private String shipToAddress3;

	private String shipToAddress4;

	private String shipToCity;

	private String shipToCountry;

	private String shipToLocation;

	private String shipToPostalCode;

	private String shipToState;

	private String shipVia;

	private String store;

	private String taxCount;

	private String taxOriginal;

	private String taxRemaining;

	private String totalItemUnitFet;

	private String transactionAttribute3;
	
	private String transactionAttribute7;
	
	private String translatorCode;
	
	private String trxCreationDate;
	
	private String trxNumber;

	private String linesCount;

	private String orderNumber;
	
	private List<InvoiceLine> invoiceLines;

}
