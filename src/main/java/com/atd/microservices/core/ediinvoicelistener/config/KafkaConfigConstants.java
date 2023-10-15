package com.atd.microservices.core.ediinvoicelistener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfigConstants {

	@Value("${kafka.groupid}")
	public  String GROUP_ID;
	@Value("${kafka.security.protocol}")
	public String SECURITY_PROTOCOL;
	@Value("${ssl.truststore.password}")
	public String SSL_TRUSTSTORE_PASSWORD;
	@Value("${ssl.truststore.location}")
	public String SSL_TRUSTSTORE_LOCATION;
	@Value("${kafka.bootstrap.server.url}")
	public String BOOTSTRAP_SERVER_URL;
	@Value("${kafka.topic.outbound}")
	public String KAFKA_TOPIC_OUTBOUND;
	@Value("${kafka.topic.inbound}")
	public String KAFKA_TOPIC_INBOUND;
	@Value("${kafka.max.message.size}")
	public String KAFKA_MAX_MESSAGE_SIZE;
}