package com.michan.movierecommendation.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class MoviesDAO {
	private static final String PK = "mov_id";
	private static final String SK = "mov_name";
	private static final String TBNAME="mn_movies";

	public void getDynamoDBItem(DynamoDbClient ddb, String mov_id, String mov_name) {

		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put(PK, AttributeValue.builder().n(mov_id).build());
		keyToGet.put(SK, AttributeValue.builder().s(mov_name).build());

		GetItemRequest request = GetItemRequest.builder().key(keyToGet).tableName(TBNAME).build();

		try {
			// If there is no matching item, GetItem does not return any data.
			Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
			if (returnedItem.isEmpty())
				System.out.format("No item found with the key %s!\n", mov_id);
			else {
				Set<String> keys = returnedItem.keySet();
				System.out.println("Amazon DynamoDB table attributes: \n");
				for (String key1 : keys) {
					System.out.format("%s: %s\n", key1, returnedItem.get(key1).toString());
				}
			}

		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
}
