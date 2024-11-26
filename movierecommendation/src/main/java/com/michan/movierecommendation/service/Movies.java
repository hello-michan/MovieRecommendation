package com.michan.movierecommendation.service;

import com.michan.movierecommendation.dao.MoviesDAO;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class Movies {
	private static final Region REGION = Region.US_EAST_2;

	public void getDetail(String tableName, String key, String keyVal) {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		moviesDao.getDynamoDBItem(ddb, tableName, key, keyVal);
		ddb.close();
	}

	public void getMovies() {
		DynamoDbClient ddb = DynamoDbClient.builder().region(REGION).build();
		MoviesDAO moviesDao = new MoviesDAO();
		moviesDao.listAllTables(ddb);

	}

	public void watchedFlag() {
	}
}
