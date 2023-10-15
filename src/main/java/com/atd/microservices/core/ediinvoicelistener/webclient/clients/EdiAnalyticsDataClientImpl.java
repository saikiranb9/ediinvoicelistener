package com.atd.microservices.core.ediinvoicelistener.webclient.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.atd.microservices.core.ediinvoicelistener.domains.exception.InvoiceException;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiData;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EdiAnalyticsDataClientImpl implements EdiAnalyticsDataClient {
	
	@Autowired
	private WebClient webClient;
	
	@Value("${spring.application.name}")
	private String applicationName;	
	
	@Value("${edi.analytics.data.url}")
	private String ediAnalyticsDataUrl;

	public Mono<EdiData> saveEDIData(EdiData ediData) {
		try { 
			return webClient.put()
				.uri(ediAnalyticsDataUrl)
				.header("XATOM-CLIENTID", applicationName)
				.body(Mono.just(ediData), EdiData.class)
				.retrieve()
				.bodyToMono(EdiData.class);
		} catch (Exception e) {
			log.error("Error while invoking EDIAnalyticsData Service's Save API", e);
			return Mono.error(new InvoiceException(
					"Error while invoking EDIAnalytics Invoice Service Save API"));
		}
	}
}
