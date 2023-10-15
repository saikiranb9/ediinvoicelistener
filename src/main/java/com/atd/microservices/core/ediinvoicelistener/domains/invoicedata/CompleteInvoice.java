package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import lombok.Data;

@Data
public class CompleteInvoice {
	
	private String type;
	
	private String standard;
	
	private String version;
	
	private String sendercode;
	
	private String receivercode;
	
	private String data;
}
