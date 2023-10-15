package com.atd.microservices.core.ediinvoicelistener.service;

import static com.atd.microservices.core.ediinvoicelistener.config.AppConstants.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ReaderUtil {

	@Value("${kafka.outbound.file.rootKey:#{null}}")
	private String rootKey;

	@Value("${kafka.outbound.file.secondaryRootKey:#{null}}")
	private String secondaryRootKey;

	@Value("${kafka.outbound.file.nodeKey:#{null}}")
	private String nodeKey;

	@Value("${kafka.outbound.file.secondaryNodeKey:#{null}}")
	private String secondaryNodeKey;

	public JsonNode getDataFromMessage(JsonNode outboundERPMessage) throws Exception {
		JsonNode finalMessage = !NOT_APPLICABLE.equalsIgnoreCase(rootKey)
				? !NOT_APPLICABLE.equalsIgnoreCase(nodeKey) ? getNodeMessage(getRootMessage(outboundERPMessage))
						: getRootMessage(outboundERPMessage)
				: outboundERPMessage;
		return finalMessage;
	}

	private JsonNode getRootMessage(JsonNode outboundERPMessage) throws Exception {
		JsonNode rootMessage = null;
		try {

			rootMessage = outboundERPMessage.findValue(rootKey);
			if (!rootMessage.isNull()) {
				return rootMessage;
			}
			/*
			 * Throw an error if initial traversal fails and there is no backup set.
			 */
			if (NOT_APPLICABLE == secondaryRootKey) {
				throw new NullPointerException("No value at " + rootKey);
			}

			rootMessage = outboundERPMessage.findValue(secondaryRootKey);
			if (rootMessage.isNull()) {
				throw new NullPointerException("No value at " + rootKey + " or " + secondaryRootKey);
			}

			return rootMessage;

		} catch (Exception e) {
			throw new Exception("Error getting root message from payload : " + e.getMessage());
		}
	}

	private JsonNode getNodeMessage(JsonNode rootMessage) throws Exception {
		JsonNode nodeMessage = null;
		try {

			nodeMessage = rootMessage.findValue(nodeKey);
			if (!nodeMessage.isNull()) {
				return nodeMessage;
			}
			/*
			 * Throw an error if initial traversal fails and there is no backup set.
			 */
			if (NOT_APPLICABLE == secondaryNodeKey) {
				throw new NullPointerException("No value at " + nodeKey);
			}

			nodeMessage = rootMessage.findValue(secondaryNodeKey);
			if (nodeMessage.isNull()) {
				throw new NullPointerException("No value at " + secondaryNodeKey);
			}

		} catch (Exception e) {
			throw new Exception("Error getting node message from payload : " + e.getMessage());
		}
		return nodeMessage;
	}
}
