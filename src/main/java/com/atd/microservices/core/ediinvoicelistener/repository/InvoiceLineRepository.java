package com.atd.microservices.core.ediinvoicelistener.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceLine;

import reactor.core.publisher.Flux;

public interface InvoiceLineRepository extends ReactiveMongoRepository<InvoiceLine, String>{

	/**
	 * Get Lines by TRX Number
	 * @param trxNumber
	 * @return
	 */
	Flux<InvoiceLine> findByTrxNumber(String trxNumber);	
}
