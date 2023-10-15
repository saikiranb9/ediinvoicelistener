package com.atd.microservices.core.ediinvoicelistener.webclient.clients;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiConfigResponse;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceDataDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EdiConfigClient {

	Flux<EdiConfigResponse> getEdiConfigData(InvoiceDataDTO invoiceDataDto);
	
	Flux<EdiConfigResponse> getAllConfigs();
}
