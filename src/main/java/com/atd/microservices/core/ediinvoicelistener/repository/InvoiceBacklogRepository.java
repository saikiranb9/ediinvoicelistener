package com.atd.microservices.core.ediinvoicelistener.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.BacklogRecord;

public interface InvoiceBacklogRepository extends ReactiveMongoRepository<BacklogRecord, String>{

}
