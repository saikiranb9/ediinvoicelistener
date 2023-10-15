package com.atd.microservices.core.ediinvoicelistener.webclient.clients;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiData;

import reactor.core.publisher.Mono;

public interface EdiAnalyticsDataClient {

	Mono<EdiData> saveEDIData(EdiData ediData);
}
