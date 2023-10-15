package com.atd.microservices.core.ediinvoicelistener.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceTax;

import reactor.core.publisher.Flux;

public interface InvoiceTaxRepository extends ReactiveMongoRepository<InvoiceTax, String>{

	/**
	 * Get Taxes by TRX Number
	 * @param trxNumber
	 * @return
	 */
	public Flux<InvoiceTax> findByTrxNumber(String trxNumber);	
}
