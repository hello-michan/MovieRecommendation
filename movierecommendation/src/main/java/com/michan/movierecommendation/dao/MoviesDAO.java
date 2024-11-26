package com.michan.movierecommendation.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import java.util.List;

public class MoviesDAO {

	public void getDynamoDBItem(DynamoDbClient ddb, String tableName, String key, String keyVal) {

		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("mov_id", AttributeValue.builder().n("2").build());
		keyToGet.put("mov_name", AttributeValue.builder().s("Barbie").build());

		GetItemRequest request = GetItemRequest.builder().key(keyToGet).tableName(tableName).build();

		try {
			// If there is no matching item, GetItem does not return any data.
			Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
			if (returnedItem.isEmpty())
				System.out.format("No item found with the key %s!\n", key);
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
	
	public static void listAllTables(DynamoDbClient ddb) {
        boolean moreTables = true;
        String lastName = null;

        while (moreTables) {
            try {
                ListTablesResponse response = null;
                if (lastName == null) {
                    ListTablesRequest request = ListTablesRequest.builder().build();
                    response = ddb.listTables(request);
                } else {
                    ListTablesRequest request = ListTablesRequest.builder()
                            .exclusiveStartTableName(lastName).build();
                    response = ddb.listTables(request);
                }

                List<String> tableNames = response.tableNames();
                if (tableNames.size() > 0) {
                    for (String curName : tableNames) {
                        System.out.format("* %s\n", curName);
                    }
                } else {
                    System.out.println("No tables found!");
                    System.exit(0);
                }

                lastName = response.lastEvaluatedTableName();
                if (lastName == null) {
                    moreTables = false;
                }

            } catch (DynamoDbException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        System.out.println("\nDone!");
    }
}
