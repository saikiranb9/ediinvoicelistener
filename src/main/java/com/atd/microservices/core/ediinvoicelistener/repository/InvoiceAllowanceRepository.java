package com.atd.microservices.core.ediinvoicelistener.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceAllowance;

import reactor.core.publisher.Flux;

public interface InvoiceAllowanceRepository extends ReactiveMongoRepository<InvoiceAllowance, String>{

	/**
	 * Get Allowances by TRX Number
	 * @param trxNumber
	 * @return
	 */
	public Flux<InvoiceAllowance> findByTrxNumber(String trxNumber);	
}
