package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Data;

@Data
public class InvoiceDataDTO implements InvoiceCommonData{

	private JsonNode invoiceHeader;
	private String trxNumber;
	private Long custAccountId;
	private Long lineCount;
	private Long taxCount;
	private Long allowanceCount;
	private ArrayNode invoiceTaxes;
	private ArrayNode invoiceAllowances;
	private ArrayNode invoiceLines;
	private EdiConfigResponse ediConfigResponse;
}
