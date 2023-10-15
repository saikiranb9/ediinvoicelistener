package com.atd.microservices.core.ediinvoicelistener.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConfig {
    @Autowired
    KafkaConfigConstants kafkaConfigConstants;

    public ConsumerFactory<String, JsonNode> consumerFactory() {
    	try {
	        Map<String, Object> config = new HashMap<>();
	        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigConstants.BOOTSTRAP_SERVER_URL);
	        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigConstants.GROUP_ID);
	        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
	        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
	        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
	
	        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaConfigConstants.SECURITY_PROTOCOL);
	        if (kafkaConfigConstants.SSL_TRUSTSTORE_LOCATION != null
	                && !StringUtils.isEmpty(kafkaConfigConstants.SSL_TRUSTSTORE_LOCATION)) {
	            config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaConfigConstants.SSL_TRUSTSTORE_LOCATION);
	        }
	        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaConfigConstants.SSL_TRUSTSTORE_PASSWORD);
	        config.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
	
	        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(JsonNode.class, false));
	    } catch (Exception e) {
			log.error("Exception in creating the Kafka Factory for consumer : {}", e);
			return null;
		}
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JsonNode> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JsonNode> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigConstants.BOOTSTRAP_SERVER_URL);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaConfigConstants.KAFKA_MAX_MESSAGE_SIZE);
        props.put("security.protocol",kafkaConfigConstants.SECURITY_PROTOCOL);
        props.put("ssl.truststore.location", kafkaConfigConstants.SSL_TRUSTSTORE_LOCATION);
        props.put("ssl.truststore.password",kafkaConfigConstants.SSL_TRUSTSTORE_PASSWORD );
        props.put("ssl.endpoint.identification.algorithm","");
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
