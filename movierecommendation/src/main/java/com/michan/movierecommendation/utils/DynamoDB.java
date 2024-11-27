package com.michan.movierecommendation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DynamoDB {

	public static String convertAttributeValueToJson(AttributeValue attributeValue) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
//	            JsonNode jsonNode = objectMapper.readTree(attributeValue.s());
	        	objectMapper.writeValueAsString(attributeValue.s());
	            return objectMapper.writeValueAsString(attributeValue.s());
	        } catch (Exception e) {
	            // Handle exceptions appropriately
	            e.printStackTrace();
	            return null;
	        }
	}
}
