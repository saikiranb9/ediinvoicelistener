package com.atd.microservices.core.ediinvoicelistener.webclient.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.EdiConfigResponse;
import com.atd.microservices.core.ediinvoicelistener.domains.invoicedata.InvoiceDataDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EdiConfigClientImpl implements EdiConfigClient {
	
	public static final String CUSTOMER_ID = "customerID";
	public static final String UMBRELLA_ID = "umbrellaid";
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${umbrella.id.value}")
	private String umbrellaIdValue;
	
	@Value("${edi.config.url}")
	private String ediConfigUrl;
	
	@Value("${all.configs.url}")
	private String allConfigsUrl;
	
	@Autowired
	WebClient webClient;
	
	public Flux<EdiConfigResponse> getEdiConfigData(InvoiceDataDTO invoiceDataDto) {
		return webClient.get()
				.uri(String.format(ediConfigUrl, CUSTOMER_ID, invoiceDataDto.getCustAccountId()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(UMBRELLA_ID, umbrellaIdValue)
                .header("XATOM-CLIENTID", applicationName)
                .retrieve().onStatus(HttpStatus::isError, exceptionFunction 
                		-> Mono.error(new Exception("EDI Config API returned Error")))
				.bodyToFlux(EdiConfigResponse.class);
	}
	
	public Flux<EdiConfigResponse> getAllConfigs() {
		return webClient.get()
				.uri(allConfigsUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(UMBRELLA_ID, umbrellaIdValue)
                .header("XATOM-CLIENTID", applicationName)
                .retrieve().onStatus(HttpStatus::isError, exceptionFunction 
                		-> Mono.error(new Exception("EDI Config API returned Error")))
				.bodyToFlux(EdiConfigResponse.class);
	}
}
