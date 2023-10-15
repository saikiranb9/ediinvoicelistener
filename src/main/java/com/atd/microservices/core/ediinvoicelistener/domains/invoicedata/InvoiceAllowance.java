package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection = "810_ALLOWANCE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceAllowance {
	@Field("EDI_ALLOWANCE_ID")
	private Long ediAllowanceId;
	
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
	private Long custAccountId;
	
	@Field("DOC_TYPE")
	private String docType;
	
	@Field("EDI_HEADER_ID")
	private Long ediHeaderId;
	
	@Field("EDI_LINE_ID")
	private Long ediLineId;
	
	@Field("LINE_NUMBER")
	private String lineNumber;
	
	@Field("PROCESS_FLAG")
	private String processFlag;
	
	@Field("PROCESS_MESSAGE")
	private String processMessage;
	
	@Field("RECORD_TYPE")
	private String recordType;
	
	@Field("TP_TRANSLATOR_CODE")
	private String tpTranslatorCode;
	
	@Field("TRX_NUMBER")
	private String trxNumber;
	
	@Field("VENDOR_COMMISSION")
	private String vendorCommission;
	
//	private Long hierarchyLevel;
//	
//	private String hierarchyLevelCode;
//	
//	private Long parentHierarchyLevel;
	
}
