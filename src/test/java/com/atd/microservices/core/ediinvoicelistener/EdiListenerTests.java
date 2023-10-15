package com.atd.microservices.core.ediinvoicelistener;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public class EdiListenerTests {
	
	static {
		System.setProperty("kafka.groupid", "invoiceheader");
		System.setProperty("kafka.security.protocol", "SSL");
		System.setProperty("kafka.bootstrap.server.url", "kafka-dev1-0.gcp.atd-us.com:31090");
		System.setProperty("kafka.topic.outbound", "DMSA856HDROB");
		System.setProperty("kafka.topic.inbound", "DMSA856HDRIB");
		System.setProperty("kafka.analytic.topic", "EDIAPIGATEWAYANALYTICS");
		System.setProperty("ssl.truststore.password", "sEQn0r1f5TJ7xnhLXMoL");
		System.setProperty("ssl.truststore.location", "src/test/resources/kafka/kafka.broker.truststore.jks");
		System.setProperty("kafka.max.message.size", "8728640");
		System.setProperty("backlog.cron.expression", "0 0/5 * * * ?");
		System.setProperty("umbrella.id.value", "test");
		System.setProperty("edi.config.url", "https://develop-edi.gcp.atd-us.com/ediconfig/?key=%1$s&value=%2$s");
		System.setProperty("all.configs.url", "https://develop-edi.gcp.atd-us.com/ediconfig/");
		System.setProperty("edi.analytics.data.url", "https://develop-edi.gcp.atd-us.com/edianalyticsdata/");
		System.setProperty("kafka.outbound.file.rootKey", "after");
		System.setProperty("kafka.outbound.file.secondaryRootKey", "N/A");
		System.setProperty("kafka.outbound.file.nodeKey", "N/A");
		System.setProperty("kafka.outbound.file.secondaryNodeKey", "N/A");
		System.setProperty("trx.number.field", "TRX_NUMBER");
		System.setProperty("customer.id.field", "CUST_ACCOUNT_ID");
		System.setProperty("line.count.field", "LINE_COUNT");
		System.setProperty("tax.count.field", "TAX_COUNT");
		System.setProperty("allowance.count.field", "ALLOWANCE_COUNT");
		System.setProperty("process.flag.field", "PROCESS_FLAG");
		System.setProperty("line.number.field", "LINE_NUMBER");
		System.setProperty("document.type.field", "DOCUMENT_TYPE");
		System.setProperty("backlog.collection", "ASN_BACKLOG");
		System.setProperty("line.collection", "810_LINE");
		System.setProperty("tax.collection", "810_TAX");
		System.setProperty("allowance.collection", "810_ALLOWANCE");
	}
	
	@Test
	public void test() {
		log.info("testing");
		assert(true);
	}
}
