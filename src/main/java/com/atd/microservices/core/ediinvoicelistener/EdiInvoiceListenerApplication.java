package com.atd.microservices.core.ediinvoicelistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class EdiInvoiceListenerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EdiInvoiceListenerApplication.class, args);
	}
	@Bean
	public WebClient getWebClientBuilder(){
		return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer
								.defaultCodecs()
								.maxInMemorySize(1024 * 1024))
						.build())
				.build();
	}

}
