package com.michan.movierecommendation.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.michan.movierecommendation.model.Movie;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class MoviesDAO {
	private static final String PK = "mov_id";
	private static final String SK = "mov_name";
	private static final String TBNAME = "mn_movies";
	private static final String CATEGORY = "mov_category";
	private static final String DESCRIPTION = "mov_description";
	private static final String RATING = "mov_rating";
	private static final String RELEASEDAY = "mov_releaseday";

	public Movie getDynamoDBItem(DynamoDbClient ddb, String mov_id, String mov_name) {
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put(PK, AttributeValue.builder().n(mov_id).build());
		keyToGet.put(SK, AttributeValue.builder().s(mov_name).build());

		GetItemRequest request = GetItemRequest.builder().key(keyToGet).tableName(TBNAME).build();
		Movie movieObj = new Movie();
		try {
			Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
			if (!returnedItem.isEmpty()) {
				movieObj.setMov_category(convertAttributeListToStringList(returnedItem.get(CATEGORY).l()));
				movieObj.setMov_description(returnedItem.get(DESCRIPTION).s());
				movieObj.setMov_rating(returnedItem.get(RATING).n());
				movieObj.setMov_releaseday(returnedItem.get(RELEASEDAY).n());
			}
			return movieObj;
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	private List<String> convertAttributeListToStringList(List<AttributeValue> output){
		List<String> converted = new ArrayList<>();
		for(AttributeValue a : output) {
			converted.add(a.s());
		}
		return converted;
	}
}
