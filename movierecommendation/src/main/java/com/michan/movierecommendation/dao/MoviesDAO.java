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
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;

public class MoviesDAO {
	private static final String PK = "mov_id";
	private static final String SK = "mov_name";
	private static final String TBNAME = "mn_movies";
	private static final String ID = "mov_id";
	private static final String NAME = "mov_name";
	private static final String CATEGORY = "mov_category";
	private static final String DESCRIPTION = "mov_description";
	private static final String RATING = "mov_rating";
	private static final String RELEASEDAY = "mov_releaseday";
	private static final String WATCHED = "mov_watched";

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
				movieObj.setMov_watched(returnedItem.get(WATCHED).bool());
			}
			return movieObj;
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	private List<String> convertAttributeListToStringList(List<AttributeValue> output) {
		List<String> converted = new ArrayList<>();
		for (AttributeValue a : output) {
			converted.add(a.s());
		}
		return converted;
	}

	public List<Movie> getDynamoDBItemsByCategory(DynamoDbClient ddb,String category) {
		List<Movie> arrMov = new ArrayList<>();
		try {
			ScanRequest scanRequest = ScanRequest.builder().tableName(TBNAME).build();
			ScanResponse response = ddb.scan(scanRequest);
			
			for (Map<String, AttributeValue> item : response.items()) {
				filterDBItemsByCategory(arrMov,category,item);
			}
		} catch (DynamoDbException e) {
			e.printStackTrace();
		}finally {
			return arrMov;
		}
	}
	
	private void filterDBItemsByCategory(List<Movie> arrMov, String category, Map<String, AttributeValue> item) {
		Movie mov = new Movie();
		List<String> converted = convertAttributeListToStringList(item.get(CATEGORY).l());
		if(converted!=null && checkCategory(converted,category)){
			mov.setMov_id(item.get(ID).n());
			mov.setMov_name(item.get(NAME).s());
//			mov.setMov_category(converted);
//			mov.setMov_description(item.get(DESCRIPTION).s());
//			mov.setMov_rating(item.get(RATING).n());
//			mov.setMov_releaseday(item.get(RELEASEDAY).n());
			arrMov.add(mov);
		}
	}
	
	private boolean checkCategory(List<String> converted, String category) {
		boolean flag = false;
		for(String s: converted) {
			if(s.equals(category)) {
				flag = true;
			}
		}
		return flag;
	}
	
	public void putDynamoDBItemByWatched(DynamoDbClient ddb, String mov_id, String mov_name, boolean updateVal) {
		HashMap<String, AttributeValue> itemKey = new HashMap<>();
        itemKey.put(PK, AttributeValue.builder().n(mov_id).build());
        itemKey.put(SK, AttributeValue.builder().s(mov_name).build());

        HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();
        updatedValues.put(WATCHED, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().bool(updateVal).build())
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(TBNAME)
                .key(itemKey)
                .attributeUpdates(updatedValues)
                .build();

        try {
            ddb.updateItem(request);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("The Amazon DynamoDB table was updated!");
    }
}
