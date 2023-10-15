package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import lombok.Data;

@Data
public class EdiConfigResponse {

	 private String customerID;
	 private String fillOrKill;
	 private String fulfillmentType;
	 private String senderCode;
	 private String receiverCode;
	 private String terminator;
	 private String delimiter;
	 private String subDelimiter;
	 private String standard810;
	 private String version810;
	 private String generate855;
	 private String storeLookUp;
	 private String generate997;
	 private String partnerName;
	 private String uuid;
}
